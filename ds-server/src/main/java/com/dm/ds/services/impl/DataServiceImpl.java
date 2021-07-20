package com.dm.ds.services.impl;

import com.dm.ds.exception.DataNotExistException;
import com.dm.ds.jdbc.QuerySql;
import com.dm.ds.jdbc.SqlBuilder;
import com.dm.ds.jdbc.SqlBuilderFactory;
import com.dm.ds.mulit.DataSourceKeyContextHolder;
import com.dm.ds.provider.DataSourceProperties;
import com.dm.ds.services.DataService;
import com.dm.ds.services.SvcService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据服务查询
 *
 * @author LiDong
 */
@Service
//@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final SvcService svcService;

    private final SqlBuilderFactory sqlBuilderFactory;

    private final NamedParameterJdbcTemplate jdbcOperations;

    public DataServiceImpl(SvcService svcService, SqlBuilderFactory sqlBuilderFactory,
                           @Qualifier("routingDataSource") DataSource dataSource) {
        this.svcService = svcService;
        this.sqlBuilderFactory = sqlBuilderFactory;
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * 根据参数查询数据结果
     *
     * @param service  服务名称
     * @param params   传入参数清单
     * @param pageable 分页信息
     * @return 查询结果
     */
    @Override
    @Cacheable(cacheNames = {"resultCache"}, sync = true)
    public Page<Map<String, Object>> queryData(
            String service,
            MultiValueMap<String, String> params,
            Pageable pageable) {
        return svcService.findByName(service).map(svc -> {
            DataSourceProperties properties = svc.getConnection();
            DataSourceKeyContextHolder.setKey(properties);
            try {
                DataSourceProperties.DbTypes type = properties.getDbType();
                // 根据数据库类型选择SQL构建器
                SqlBuilder sqlBuilder = sqlBuilderFactory.getBuilder(type);
                QuerySql querySql = sqlBuilder.getQuerySql(svc, params, pageable);
                Long count = jdbcOperations.queryForObject(querySql.getCountSql(), querySql.getParameters(), Long.class);
                count = Optional.ofNullable(count).orElse(0L);
                List<Map<String, Object>> content = jdbcOperations.queryForList(querySql.getQuerySql(), querySql.getParameters());
                return new PageImpl<>(content, pageable, count);
            } finally {
                DataSourceKeyContextHolder.clear();
            }
        }).orElseThrow(DataNotExistException::new);
    }
}
