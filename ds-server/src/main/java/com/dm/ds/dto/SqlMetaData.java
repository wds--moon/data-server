package com.dm.ds.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Data;

/**
 * SQL语句的元数据信息
 *
 * @author LiDong
 */
@Data
public class SqlMetaData implements Serializable {
    private static final long serialVersionUID = 2785945493552228265L;
    /**
     * 语句中的必填参数
     */
    private Set<String> parameters;
    /**
     * 语句的输出列信息
     */
    private List<ColumnMetaData> columns;

    public SqlMetaData(Set<String> parameters, List<ColumnMetaData> columns) {
        super();
        this.parameters = parameters;
        this.columns = columns;
    }

    public SqlMetaData() {
        super();
    }

}
