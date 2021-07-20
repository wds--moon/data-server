package com.dm.ds.services.impl;

import com.dm.ds.entity.DmDataSource;
import com.dm.ds.repositories.DmDataSourceRepository;
import com.dm.ds.collections.CollectionUtils;

import com.dm.ds.converter.DmDataSourceConverter;
import com.dm.ds.exception.DuplicateDataException;
import com.dm.ds.converter.SvcConverter;
import com.dm.ds.dto.ColumnMetaData;
import com.dm.ds.dto.SqlMetaData;
import com.dm.ds.dto.SvcDto;
import com.dm.ds.dto.SvcInfo;
import com.dm.ds.entity.QSvc;
import com.dm.ds.entity.Svc;
import com.dm.ds.jdbc.impl.ParsedSql;
import com.dm.ds.jdbc.impl.SqlUtils;
import com.dm.ds.mulit.DataSourceKeyContextHolder;
import com.dm.ds.provider.DataSourceProperties;
import com.dm.ds.repositories.SvcRepository;
import com.dm.ds.services.SvcService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/*
 * 缓存策略，当服务改变时,清除所有服务相关的缓存，以及依赖于服务信息的缓存
 */

/**
 * 服务配置相关的服务，包括服务的添加，删除和修改
 *
 * @author LiDong
 */
@Service
@RequiredArgsConstructor
public class SvcServiceImpl implements SvcService {

    private final SvcRepository svcRepository;

    private final SvcConverter svcConverter;

    private final DmDataSourceRepository cnnRepository;

    private final QSvc qSvc = QSvc.svc;

    private DataSource dataSource = null;

    private final DmDataSourceConverter dataSourceConverter;


    @Autowired
    @Qualifier("routingDataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 每当有服务更新或者删除时，都刷新缓存，很暴力，但相对简单
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = {"svcCache", "resultCache"}, allEntries = true)
    public Svc save(SvcDto dto) {
        preCheck(null, dto.getName());
        Svc svc = new Svc();
        svcConverter.copyProperties(svc, dto);
        Long cnnId = dto.getConnectionId();
        DmDataSource cnn = cnnRepository.getOne(cnnId);
        svc.setConnection(cnn);
        return svcRepository.save(svc);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"svcCache", "resultCache"}, allEntries = true)
    public void deleteById(Long id) {
        if (svcRepository.existsById(id)) {
            Svc svc = svcRepository.getOne(id);
            svc.setDeleted(Boolean.TRUE);
            svcRepository.save(svc);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @CacheEvict(cacheNames = {"svcCache", "resultCache"}, allEntries = true)
    public Svc update(Long id, SvcDto dto) {
        preCheck(id, dto.getName());
        Svc svc = svcRepository.getOne(id);
        svcConverter.copyProperties(svc, dto);
        Long cnnId = dto.getConnectionId();
        DmDataSource cnn = cnnRepository.getOne(cnnId);
        svc.setConnection(cnn);
        // 进行版本检查，防止并发更新
        svc.checkVersion(dto.getVersion());
        return svcRepository.saveAndFlush(svc);
    }

    @Override
    public Optional<Svc> findById(Long id) {
        return svcRepository.findById(id);
    }

    @Override
    @Cacheable(cacheNames = "svcCache", key = "'name-'+#service", sync = true)
    public Optional<SvcInfo> findByName(String service) {
        return svcRepository.findByNameAndDeletedIsFalse(service).map(svcConverter::toSvcInfo);
    }

    @Override
    public Optional<SqlMetaData> getMeta(SvcDto svc) throws SQLException {
        Optional<DmDataSource> dataSourceOptional = cnnRepository.findById(svc.getConnectionId());
        if (dataSourceOptional.isPresent()) {
            DmDataSource cnn = dataSourceOptional.get();
            List<ColumnMetaData> columnMetas = getColumnMetas(cnn, svc.getSql());
            List<String> parameterNames = getParameterNames(svc.getSql());
            return Optional.of(new SqlMetaData(new LinkedHashSet<>(parameterNames), columnMetas));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public SqlMetaData getMeta(DmDataSource cnn, String sql) throws SQLException {
        List<ColumnMetaData> columnMetas = getColumnMetas(cnn, sql);
        List<String> parameterNames = getParameterNames(sql);
        return new SqlMetaData(new LinkedHashSet<>(parameterNames), columnMetas);
    }

    @Override
    public Page<Svc> list(final String search, final Set<String> label, Pageable pageable) {
        BooleanBuilder queryBuilder = new BooleanBuilder().and(qSvc.deleted.isFalse());
        if (StringUtils.isNotBlank(search)) {
            queryBuilder.and(qSvc.name.containsIgnoreCase(search)
                .or(qSvc.description.containsIgnoreCase(search))
                .or(qSvc.labels.any().containsIgnoreCase(search)));
        }
        if (CollectionUtils.isNotEmpty(label)) {
            queryBuilder.and(qSvc.labels.any().in(label));
        }
        return svcRepository.findAll(queryBuilder, pageable);
    }

    /**
     * 获取标签列表
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<String> listLabels(Pageable pageable) {
        return svcRepository.findLabels(pageable);
    }

    @Override
    public Svc patch(@NotNull Long id, SvcDto dto) {
        throw new NotImplementedException("该方法未实现");
    }

    /**
     * 获取列信息
     *
     * @param dbConnection
     * @param sql
     * @return
     * @throws SQLException
     */
    private List<ColumnMetaData> getColumnMetas(DmDataSource dbConnection, String sql) throws SQLException {
        String metaSql = NamedParameterUtils.parseSqlStatementIntoString(sql);
        DataSourceProperties properties = dataSourceConverter.toDataSourceProperties(dbConnection);
        DataSourceKeyContextHolder.setKey(properties);
//        metaSql.toCharArray();
        try (Connection cnn = dataSource.getConnection();
             PreparedStatement statement = cnn.prepareStatement(metaSql)) {
            ResultSetMetaData metaData = statement.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<ColumnMetaData> result = new LinkedList<>();
            for (int i = 1; i <= columnCount; i++) {
                ColumnMetaData metaData_ = new ColumnMetaData(metaData.getColumnName(i), metaData.getColumnLabel(i),
                    metaData.getColumnType(i), metaData.getColumnTypeName(i));
                result.add(metaData_);
            }
            return result;
        } finally {
            DataSourceKeyContextHolder.clear();
        }
    }

    private List<String> getParameterNames(String sql) {
        ParsedSql ps = SqlUtils.parseSqlStatement(sql);
        return ps.getParameterNames();
    }

    private void preCheck(Long id, String name) {
        if (svcRepository.existsByNameIgnoreCaseAndDeletedIsFalse(id, name)) {
            throw new DuplicateDataException();
        }
    }
}
