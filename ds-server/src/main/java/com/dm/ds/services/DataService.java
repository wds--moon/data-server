package com.dm.ds.services;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

public interface DataService {

    Page<Map<String, Object>> queryData(String service, MultiValueMap<String, String> params, Pageable pageable);

}
