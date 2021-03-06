package com.dm.ds.mulit;

import com.dm.ds.provider.DataSourceProperties;

public class DataSourceKeyContextHolder {

    private static DetermineCurrentLookupKeyStrategy strategy;

    static {
        initialize();
    }

    private static void initialize() {
        strategy = new ThreadLocalDetermineCurrentLookupKeyStrategy();
    }

    public static void clear() {
        strategy.clear();
    }

    public static void setKey(DataSourceProperties properties) {
        strategy.setKey(properties);
    }

    public static DataSourceProperties determineCurrentLookupKey() {
        return strategy.determineCurrentLookupKey();
    }
}
