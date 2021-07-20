package com.dm.ds.jdbc;

import java.io.Serializable;
import java.util.Map;

/**
 * 构建一次查询所必要的相关信息，<br />
 * 包括查询总数需要的语句，查询语句，以及查询参数
 *
 * @author LiDong
 */
public interface QuerySql extends Serializable {

    /**
     * 查询总数的语句
     *
     * @return
     */
    String getCountSql();

    /**
     * 实际的查询语句
     *
     * @return
     */
    String getQuerySql();

    /**
     * 实际使用的参数列表
     *
     * @return
     */
    Map<String, Object> getParameters();

}
