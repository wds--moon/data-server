package com.dm.ds.jdbc.impl;

import com.dm.ds.provider.DataSourceProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class SQLServerSqlBuilder extends AbstractSqlBuilder {

    @Override
    protected char getColumnSplitCharStart() {
        return '[';
    }

    @Override
    protected char getColumnSplitCharEnd() {
        return ']';
    }

    @Override
    protected DataSourceProperties.DbTypes getDbType() {
        return DataSourceProperties.DbTypes.SQLSERVER;
    }

    @Override
    protected String buildPagedSql(StringBuilder builder, Pageable pageable) {
        builder.append(" OFFSET ").append(pageable.getOffset()).append(" ROWS FETCH NEXT ")
                .append(pageable.getPageSize()).append(" ROWS ONLY");
        return builder.toString();
    }
}
