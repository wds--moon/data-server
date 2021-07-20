package com.dm.ds.provider.support;

import com.dm.ds.collections.Sets;
import com.dm.ds.provider.DataSourceProperties;
import com.dm.ds.provider.DataSourceProvider;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.DmDialect;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DMSourceProvider implements DataSourceProvider {

    private final Dialect dialect = new DmDialect();

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public Set<DataSourceProperties.DbTypes> getSupportDbTypes() {
        return Sets.hashSet(DataSourceProperties.DbTypes.DM);
    }

    @Override
    public String getDriverClassName() {
        return "dm.jdbc.driver.DmDriver";
    }

    @Override
    public String getTestQuery() {
        return " select 1 ";
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
                "jdbc:dm://",
                info.getHost(),
                ":",
                info.getPort(),
                "/",
                info.getDatabase(),
                query);

    }
}
