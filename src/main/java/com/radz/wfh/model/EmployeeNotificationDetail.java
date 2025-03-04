package com.radz.wfh.model;

import com.radz.wfh.constant.WfhConstants;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "EMPLOYEE_NOTIFICATION_DETAIL")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeNotificationDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long notificationId;

  private String employeeId;

  private String message;

  @Builder.Default
  @Column(nullable = false, updatable = false)
  private String createdBy = WfhConstants.APP_USER;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdTimestamp;
}
