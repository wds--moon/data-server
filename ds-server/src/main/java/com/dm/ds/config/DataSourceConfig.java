package com.dm.ds.config;

import com.dm.ds.mulit.AutoCreateRoutingDataSource;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean("routingDataSource")
    public AutoCreateRoutingDataSource dataSource(DataSource platformDataSource) {
        AutoCreateRoutingDataSource dataSource = new AutoCreateRoutingDataSource();
        dataSource.setDefaultTargetDataSource(platformDataSource);
        return dataSource;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariDataSource platformDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public JPAQueryFactory jpaQuery(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

}
