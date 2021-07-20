package com.dm.ds.entity;

import com.dm.ds.dto.SvcDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Order implements Serializable {

    /**
     * 排序方式
     *
     * @author LiDong
     */
    public enum Direction {
        /**
         * 升序排列
         */
        ASC,
        /**
         * 降序排列
         */
        DESC
    }

    private static final long serialVersionUID = 5290684778590238497L;

    @NotBlank(groups = SvcDto.Default.class)
    @Column(name = "column_", length = 200)
    private String column;

    @NotNull(groups = SvcDto.Default.class)
    @Column(name = "direction_", length = 10)
    @Enumerated(EnumType.STRING)
    private Direction direction;

}
