package com.dm.ds.dto;

import com.dm.ds.entity.Order;
import com.dm.ds.entity.Parameter;
import com.dm.ds.provider.DataSourceProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 一个最基本的服务信息，包含执行某个服务所必须的信息
 *
 * @author LiDong
 */
@Data
public class SvcInfo implements Serializable {

    private static final long serialVersionUID = 8416595887970443587L;

    private Long id;

    private String name;

    private DataSourceProperties connection;

    private String sql;

    private List<Parameter> parameters;

    private List<Order> orders;

    public SvcInfo() {

    }

}
