package com.dm.ds.services.impl;

import com.dm.ds.converter.DmDataSourceConverter;
import com.dm.ds.dto.DmDataSourceDto;
import com.dm.ds.entity.DmDataSource;
import com.dm.ds.entity.QDmDataSource;
import com.dm.ds.exception.DataNotExistException;
import com.dm.ds.jdbc.ConnectionUtils;
import com.dm.ds.jdbc.TableMeta;
import com.dm.ds.mulit.DataSourceHolder;
import com.dm.ds.provider.DataSourceProviderHolder;
import com.dm.ds.repositories.DmDataSourceRepository;
import com.dm.ds.services.DmDataSourceService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Service
public class DmDataSourceServiceImpl implements DmDataSourceService {

    private final DmDataSourceConverter dmDataSourceConverter;
    private final DmDataSourceRepository dmDataSourceRepository;
    private final QDmDataSource qDataSource = QDmDataSource.dmDataSource;

    private DataSourceHolder dataSourceHolder;

    @Autowired(required = false)
    public void setDataSourceHolder(DataSourceHolder dataSourceHolder) {
        this.dataSourceHolder = dataSourceHolder;
    }

    public DmDataSourceServiceImpl(
            DmDataSourceConverter dataSourceConverter,
            DmDataSourceRepository dataSourceRepository) {
        this.dmDataSourceConverter = dataSourceConverter;
        this.dmDataSourceRepository = dataSourceRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DmDataSource save(DmDataSourceDto connection) {
        DmDataSource cnn = new DmDataSource();
        dmDataSourceConverter.copyProperties(cnn, connection);
        return dmDataSourceRepository.save(cnn);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DmDataSource update(Long id, DmDataSourceDto connection) {
        return dmDataSourceRepository.findById(id).map(cnn -> {
            closeConnection(cnn);
            DmDataSource result = dmDataSourceConverter.copyProperties(cnn, connection);
            result.checkVersion(connection.getVersion());
            return dmDataSourceRepository.saveAndFlush(result);
        }).orElseThrow(DataNotExistException::new);
    }

    @Override
    public Optional<DmDataSource> findById(Long id) {
        return dmDataSourceRepository.findById(id);
    }

    @Override
    public Page<DmDataSource> list(String keyword, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        if (StringUtils.isNotBlank(keyword)) {
            keyword = keyword.trim();
            query.and(qDataSource.name.containsIgnoreCase(keyword)
                    .or(qDataSource.host.containsIgnoreCase(keyword))
                    .or(qDataSource.username.containsIgnoreCase(keyword)));
        }
        return dmDataSourceRepository.findAll(query, pageable);
    }

    @Override
    public List<DmDataSource> listAll() {
        return dmDataSourceRepository.findAll();
    }

    @Override
    public List<TableMeta> listTables(Long connection) {
        return dmDataSourceRepository.findById(connection)
                .map(dmDataSourceConverter::toDataSourceProperties)
                .map(properties -> {
                    try (Connection cnn = DataSourceProviderHolder.getConnection(properties)) {
                        return ConnectionUtils.listTables(cnn);
                    } catch (Exception throwable) {
                        throw new RuntimeException(throwable);
                    }
                }).orElseThrow(DataNotExistException::new);
    }

    /**
     * 将连接从池中移除,并关闭连接池
     *
     * @param dataSource 要移除的数据源
     */
    private void closeConnection(DmDataSource dataSource) {
        if (this.dataSourceHolder != null) {
            dataSourceHolder.closeAndRemove(dmDataSourceConverter.toDataSourceProperties(dataSource));
        }
    }
}
