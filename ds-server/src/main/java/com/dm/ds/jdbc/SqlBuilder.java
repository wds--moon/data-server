package com.dm.ds.jdbc;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import com.dm.ds.dto.SvcInfo;

public interface SqlBuilder {

    QuerySql getQuerySql(SvcInfo svc, MultiValueMap<String, String> params, Pageable pageable);

}
