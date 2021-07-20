package com.dm.ds.provider.support;

import com.dm.ds.collections.Sets;
import com.dm.ds.provider.DataSourceProperties;
import com.dm.ds.provider.DataSourceProvider;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle12cDialect;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OracleDataSourceProvider implements DataSourceProvider {

    private final Dialect dialect = new Oracle12cDialect();

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public Set<DataSourceProperties.DbTypes> getSupportDbTypes() {
        return Sets.hashSet(DataSourceProperties.DbTypes.Oracle);
    }

    @Override
    public String getDriverClassName() {
        return "oracle.jdbc.driver.OracleDriver";
    }

    @Override
    public String getTestQuery() {
        return "select 1 from dual";
    }

    @Override
    public String getUrl(DataSourceProperties info) {
        Map<String, String> additionalProperties = info.getAdditionalProperties();
        String query = "";
        if (!additionalProperties.isEmpty()) {
            query = StringUtils.join("?", additionalProperties.entrySet().stream().map(entry -> StringUtils.join(entry.getKey(), "=", entry.getValue()))
                    .collect(Collectors.joining("&")));
        }
        return StringUtils.join(
                "jdbc:oracle:thin:@",
                info.getHost(),
                ":",
                info.getPort(),
                ":",
                info.getDatabase(),
                query);
    }
}
