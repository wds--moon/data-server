package com.dm.ds.dto;

import com.dm.ds.entity.Order;
import com.dm.ds.entity.Parameter;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class SvcDto implements Serializable {
    private static final long serialVersionUID = -6098284924857636923L;

    /**
     * 新增服务的校验分组
     */
    public interface New extends Default {
    }

    /**
     * 更新服务的校验分组
     */
    public interface Update extends Default {
    }


    public interface Default {

    }

    private Long id;

    @NotNull(groups = {New.class, Update.class})
    private Long connectionId;

    @NotBlank(groups = {New.class, Update.class})
    @Size(max = 4000, groups = {Default.class})
    private String sql;

    @NotBlank(groups = {New.class, Update.class})
    @Size(max = 200, groups = {Default.class})
    private String name;

    @Size(max = 8000, groups = {Default.class})
    private String description;

    // TODO 必要参数的校验需要单独处理
    //    @Valid
    private List<Parameter> requiredParameters;

    @Valid
    private List<Parameter> parameters;

    @NotEmpty(groups = {Default.class, New.class})
    @Valid
    private List<Order> orders;

    private Set<String> labels;


    private Map<String, String> columns;


    @NotNull(groups = {Update.class})
    private Long version;

}
