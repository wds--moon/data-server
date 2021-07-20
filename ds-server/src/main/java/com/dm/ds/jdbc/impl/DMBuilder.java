package com.dm.ds.jdbc.impl;

import com.dm.ds.provider.DataSourceProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DMBuilder extends AbstractSqlBuilder {

    @Override
    protected char getColumnSplitCharStart() {
        return '"';
    }

    @Override
    protected char getColumnSplitCharEnd() {
        return '"';
    }

    @Override
    protected DataSourceProperties.DbTypes getDbType() {
        return DataSourceProperties.DbTypes.DM;
    }

    @Override
    protected String buildPagedSql(StringBuilder builder, Pageable pageable) {
        builder.append(" limit ").append(pageable.getOffset()).append(",").append(pageable.getPageSize());
        return builder.toString();
    }
}
