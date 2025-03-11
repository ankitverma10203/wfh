package com.radz.wfh.dto;

import com.radz.wfh.constant.NotificationType;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeNotificationData {

  private Long notificationId;

  private NotificationType notificationType;

  private String message;

  private String createdTimestamp;
}
