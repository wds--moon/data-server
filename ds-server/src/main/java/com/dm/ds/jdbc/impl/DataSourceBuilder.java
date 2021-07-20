package com.dm.ds.jdbc.impl;

import com.dm.ds.provider.DataSourceProperties;

import javax.sql.DataSource;

public interface DataSourceBuilder {
    DataSource buildDataSource(DataSourceProperties properties);
}
