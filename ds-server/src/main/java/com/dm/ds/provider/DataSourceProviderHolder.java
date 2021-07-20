package com.dm.ds.provider;

import com.dm.ds.jdbc.ConnectionUtils;
import com.dm.ds.provider.DataSourceProperties.DbTypes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DataSourceProviderHolder {

    private static Map<DbTypes, DataSourceProvider> providers = new ConcurrentHashMap<>();


    private DataSourceProviderHolder() {

    }

    public static void registerProvider(DataSourceProvider provider) {
        provider.getSupportDbTypes().forEach(dbType -> providers.put(dbType, provider));
    }

    public static DataSourceProvider getProvider(DataSourceProperties properties) {
        return providers.get(properties.getDbType());
    }

    public static DataSourceProvider getProvider(DbTypes type) {
        return providers.get(type);
    }

    public static Connection getConnection(DataSourceProperties properties)
            throws SQLException, ClassNotFoundException {
        DbTypes dbType = properties.getDbType();
        DataSourceProvider provider = getProvider(dbType);
        return ConnectionUtils.createConnection(provider.getUrl(properties), provider.getDriverClassName(),
                properties.getUsername(), properties.getPassword());
    }
}
