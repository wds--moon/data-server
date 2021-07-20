package com.dm.ds.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class TableMetaDto implements Serializable {
    private static final long serialVersionUID = -6247444957114695391L;
    private String name;
    private String type;
}
