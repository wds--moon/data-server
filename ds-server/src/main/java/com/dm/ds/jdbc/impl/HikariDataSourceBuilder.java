package com.dm.ds.jdbc.impl;

import com.dm.ds.jdbc.impl.DataSourceBuilder;
import com.dm.ds.provider.DataSourceProperties;
import com.dm.ds.provider.DataSourceProvider;
import com.dm.ds.provider.DataSourceProviderHolder;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Objects;

public class HikariDataSourceBuilder implements DataSourceBuilder {
    @Override
    public DataSource buildDataSource(DataSourceProperties properties) {
        DataSourceProvider provider = DataSourceProviderHolder.getProvider(properties.getDbType());
        if (Objects.isNull(provider)) {
            return null;
        } else {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName(provider.getDriverClassName());
            dataSource.setJdbcUrl(provider.getUrl(properties));
            dataSource.setUsername(properties.getUsername());
            dataSource.setPassword(properties.getPassword());
            dataSource.setMinimumIdle(0);
            dataSource.setConnectionTestQuery(provider.getTestQuery());
            return dataSource;
        }
    }
}
