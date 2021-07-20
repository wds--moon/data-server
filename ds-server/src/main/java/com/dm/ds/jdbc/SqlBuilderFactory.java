package com.dm.ds.jdbc;

import com.dm.ds.provider.DataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SqlBuilderFactory {

    private final Map<DataSourceProperties.DbTypes, SqlBuilder> map = new HashMap<>();

    /**
     * 根据数据类型，获取Sql构建器
     *
     * @param type
     * @return
     */
    public SqlBuilder getBuilder(DataSourceProperties.DbTypes type) {
        return map.get(type);
    }

    public void register(DataSourceProperties.DbTypes type, SqlBuilder builder) {
        log.info("Register a sqlBuilder [" + builder + "] on type [" + type + "]");
        map.put(type, builder);
    }

}
