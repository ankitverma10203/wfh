package com.radz.wfh.model;

import com.radz.wfh.constant.WfhConstants;
import com.radz.wfh.constant.WfhType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Builder
@Entity
@Table(name = "WFH_QUANTITY_REF")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WfhQuantityRef {

  @Enumerated(EnumType.STRING)
  @Id private WfhType wfhType;

  @Column(nullable = false)
  private long quantity;

  @Builder.Default
  @Column(nullable = false, updatable = false)
  private String createdBy = WfhConstants.APP_USER;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdTimestamp;

  @Builder.Default
  @Column(nullable = false)
  private String updatedBy = WfhConstants.APP_USER;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedTimestamp;
}
