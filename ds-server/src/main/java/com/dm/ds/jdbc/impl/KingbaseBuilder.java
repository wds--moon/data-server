package com.dm.ds.jdbc.impl;

import com.dm.ds.provider.DataSourceProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author wendongshan
 */
@Component
public class KingbaseBuilder extends AbstractSqlBuilder {

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
        return DataSourceProperties.DbTypes.Kingbase;
    }

    @Override
    protected String buildPagedSql(StringBuilder builder, Pageable pageable) {
        builder.append(" limit ").append(pageable.getPageSize()).append(" OFFSET ").append(pageable.getOffset());
        return builder.toString();
    }
}
