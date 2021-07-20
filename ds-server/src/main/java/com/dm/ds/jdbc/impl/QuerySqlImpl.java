package com.dm.ds.jdbc.impl;

import java.util.Map;

import com.dm.ds.jdbc.QuerySql;

import lombok.Getter;

@Getter
class QuerySqlImpl implements QuerySql {

    private static final long serialVersionUID = 1561118176208758189L;

    private final String countSql;

    private final String querySql;

    // private final String metaSql;

    private final Map<String, Object> parameters;

    public QuerySqlImpl(String countSql, String querySql, Map<String, Object> params) {
        super();
        this.countSql = countSql;
        this.querySql = querySql;
        this.parameters = params;
        // this.metaSql = metaSql;
    }

}
