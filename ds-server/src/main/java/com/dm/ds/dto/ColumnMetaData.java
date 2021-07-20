package com.dm.ds.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ColumnMetaData implements Serializable {
    private static final long serialVersionUID = -5661733899135885747L;
    private String columnName;
    private String columnLabel;
    private int columnType;
    private String columnTypeName;

    public ColumnMetaData(String columnName, String columnLabel, int columnType, String columnTypeName) {
        super();
        this.columnName = columnName;
        this.columnLabel = columnLabel;
        this.columnType = columnType;
        this.columnTypeName = columnTypeName;
    }

    public ColumnMetaData() {

    }

}
