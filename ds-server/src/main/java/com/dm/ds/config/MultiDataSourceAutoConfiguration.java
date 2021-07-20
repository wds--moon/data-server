package com.dm.ds.config;


import com.dm.ds.jdbc.impl.DataSourceBuilder;
import com.dm.ds.jdbc.impl.HikariDataSourceBuilder;
import com.dm.ds.mulit.AutoCreateRoutingDataSource;
import com.dm.ds.mulit.DetermineCurrentLookupKeyStrategy;
import com.dm.ds.mulit.ThreadLocalDetermineCurrentLookupKeyStrategy;
import com.dm.ds.provider.support.DMSourceProvider;
import com.dm.ds.provider.DataSourceProvider;
import com.dm.ds.provider.DataSourceProviderHolder;
import com.dm.ds.provider.support.KingbaseSourceProvider;
import com.dm.ds.provider.support.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnBean(AutoCreateRoutingDataSource.class)
public class MultiDataSourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(DetermineCurrentLookupKeyStrategy.class)
    public DetermineCurrentLookupKeyStrategy determineCurrentLookupKeyStrategy() {
        return new ThreadLocalDetermineCurrentLookupKeyStrategy();
    }

    @Bean
    @ConditionalOnClass(name = "com.microsoft.sqlserver.jdbc.SQLServerDriver")
    public SqlServerDataSourceProvider sqlServerDataSourceProvider() {
        return new SqlServerDataSourceProvider();
    }

    @Bean
    @ConditionalOnClass(name = "com.mysql.cj.jdbc.Driver")
    public MySQL8DataSourceProvider mySQL8DataSourceProvider() {
        return new MySQL8DataSourceProvider();
    }

    @Bean
    @ConditionalOnClass(name = "oracle.jdbc.driver.OracleDriver")
    public OracleDataSourceProvider oracleDataSourceProvider() {
        return new OracleDataSourceProvider();
    }

    @Bean
    @ConditionalOnClass(name = "org.h2.Driver")
    public H2DataSourceProvider h2DataSourceProvider() {
        return new H2DataSourceProvider();
    }

    @Bean
    @ConditionalOnClass(name = "org.postgresql.Driver")
    public PostgreSQLDataSourceProvider postgreSQLDataSourceProvider() {
        return new PostgreSQLDataSourceProvider();
    }

    @Bean
    @ConditionalOnClass(name = "dm.jdbc.driver.DmDriver")
    public DMSourceProvider dMSourceProvider() {
        return new DMSourceProvider();
    }

    @Bean
    @ConditionalOnClass(name = "com.kingbase8.Driver")
    public KingbaseSourceProvider kingbaseSourceProvider() {
        return new KingbaseSourceProvider();
    }

    @Bean
    @ConditionalOnMissingBean(DataSourceBuilder.class)
    @ConditionalOnClass(name = "com.zaxxer.hikari.HikariDataSource")
    public DataSourceBuilder dataSourceBuilder() {
        return new HikariDataSourceBuilder();
    }


    @Configuration
    static class DataSourceProviderConfiguration {
        DataSourceProviderConfiguration(List<DataSourceProvider> providers) {
            providers.forEach(DataSourceProviderHolder::registerProvider);
        }
    }
}
