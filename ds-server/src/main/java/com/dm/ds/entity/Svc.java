package com.dm.ds.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "service_", indexes = {
        @Index(name = "idx_name_deleted_", columnList = "name_,deleted_"),
        @Index(name = "idx_deleted_", columnList = "deleted_"),
        @Index(name = "idx_name_", columnList = "name_")
})
public class Svc extends AbstractEntity {

    @ManyToOne
    private DmDataSource connection;

    @Column(name = "name_", nullable = false)
    private String name;

    @Lob
    @Column(name = "sql_")
    @Basic(fetch = FetchType.LAZY)
    private String sql;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "service_parameters_", joinColumns = {
            @JoinColumn(name = "service_id_")
    }, uniqueConstraints = {
            @UniqueConstraint(columnNames = {"service_id_", "parameter_name_"})
    })
    @OrderColumn(name = "order_")
    private List<Parameter> parameters;

    @ElementCollection(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "service_labels_", joinColumns = {
            @JoinColumn(name = "service_id_")
    }, uniqueConstraints = {
            @UniqueConstraint(columnNames = {"service_id_", "label_"})
    })
    @Column(name = "label_")
    private Set<String> labels;

    @ElementCollection
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "service_required_parameters_", joinColumns = {
            @JoinColumn(name = "service_id_")
    })
    @OrderColumn(name = "order_")
    private List<Parameter> requiredParameters;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "service_orders_", joinColumns = {
            @JoinColumn(name = "service_id_")
    }, uniqueConstraints = {
            @UniqueConstraint(columnNames = {"service_id_", "column_"})
    })
    @OrderColumn(name = "order_")
    private List<Order> orders;

    /**
     * 包含的列的输出字段的说明
     */
    @ElementCollection
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "service_columns_", joinColumns = {
            @JoinColumn(name = "service_id_")
    })
    @Column(name = "description_")
    @MapKeyColumn(name = "column_name_")
    private Map<String, String> columns;

    @Lob
    @Column(name = "description_")
    @Basic(fetch = FetchType.LAZY)
    private String description;

    /**
     * 指定服务是否被删除
     */
    @Column(name = "deleted_")
    private boolean deleted = false;
}
