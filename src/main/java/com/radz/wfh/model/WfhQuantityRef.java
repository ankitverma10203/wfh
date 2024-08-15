package com.radz.wfh.model;

import com.radz.wfh.constant.WfhType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "WFH_QUANTITY_REF")
@Getter
@Setter
public class WfhQuantityRef {

    @Id
    private WfhType wfhType;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String createdBy;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdTimestamp;

    private String updatedBy;

    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;
}
