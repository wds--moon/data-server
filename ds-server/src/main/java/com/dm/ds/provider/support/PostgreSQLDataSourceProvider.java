package com.dm.ds.provider.support;

import com.dm.ds.collections.Sets;
import com.dm.ds.provider.DataSourceProperties;
import com.dm.ds.provider.DataSourceProvider;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.PostgreSQL95Dialect;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PostgreSQLDataSourceProvider implements DataSourceProvider {

    private final Dialect dialect = new PostgreSQL95Dialect();

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public Set<DataSourceProperties.DbTypes> getSupportDbTypes() {
        return Sets.hashSet(DataSourceProperties.DbTypes.PostgreSQL);
    }

    @Override
    public String getDriverClassName() {
        return "org.postgresql.Driver";
    }

    @Override
    public String getUrl(DataSourceProperties info) {
        Map<String, String> additionalProperties = info.getAdditionalProperties();
        String query = StringUtils.join("?", additionalProperties.entrySet().stream().map(entry -> StringUtils.join(entry.getKey(), "=", entry.getValue()))
                .collect(Collectors.joining("&")));
        return StringUtils.join(
                "jdbc:postgresql://",
                info.getHost(),
                ":",
                info.getPort(),
                "/",
                info.getDatabase(),
                query);
    }
}
