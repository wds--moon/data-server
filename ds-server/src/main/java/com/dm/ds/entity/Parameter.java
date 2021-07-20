package com.dm.ds.entity;

import com.dm.ds.dto.SvcDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Parameter implements Serializable {

    private static final long serialVersionUID = 2203811797498404695L;

    @NotBlank(groups = {SvcDto.Default.class})
    @Size(max = 100, groups = {SvcDto.Default.class})
    @Column(name = "column_", length = 100)
    private String column;

    @Size(max = 200, groups = {SvcDto.Default.class})
    @Column(name = "description_", length = 200)
    private String description;

    @NotBlank(groups = {SvcDto.Default.class})
    // 因为必要参数没有校验比较符号，所以这里不能校验必要参数
    @Size(max = 100, groups = {SvcDto.Default.class})
    @Column(name = "operator_", length = 10)
    private String operator;

    @NotBlank(groups = {SvcDto.Default.class})
    @Size(max = 100, groups = {SvcDto.Default.class})
    @Column(name = "parameter_name_", length = 200)
    private String parameterName;

}
