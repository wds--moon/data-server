package com.dm.ds.jdbc.impl;

import com.dm.ds.provider.DataSourceProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author wendongshan
 */
@Component
public class OracleBuilder extends AbstractSqlBuilder {

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
        return DataSourceProperties.DbTypes.Oracle;
    }

    @Override
    protected String buildPagedSql(StringBuilder sql, Pageable pageable) {

        Long startRow = pageable.getOffset();
        Long endRow = pageable.getOffset() + pageable.getPageSize();
        StringBuilder sqlBuilder = new StringBuilder(sql.length() + 120);
        if (startRow > 0) {
            sqlBuilder.append("SELECT * FROM ( ");
        }
        if (endRow > 0) {
            sqlBuilder.append(" SELECT TMP_PAGE.*, ROWNUM ROW_ID FROM ( ");
        }
        sqlBuilder.append(sql);
        if (endRow > 0) {
            sqlBuilder.append(" ) TMP_PAGE WHERE ROWNUM <= ");
            sqlBuilder.append(endRow);
        }
        if (startRow > 0) {
            sqlBuilder.append(" ) WHERE ROW_ID > ");
            sqlBuilder.append(startRow);
        }

        return sqlBuilder.toString();
    }
}
