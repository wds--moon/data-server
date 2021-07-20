package com.dm.ds.mulit;

import com.dm.ds.provider.DataSourceProperties;

import javax.sql.DataSource;

public interface DataSourceHolder {
    void closeAndRemove(DataSourceProperties properties);

    DataSource add(DataSourceProperties properties);
}
