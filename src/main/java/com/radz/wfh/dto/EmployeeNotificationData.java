package com.radz.wfh.dto;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeNotificationData {

  private Long notificationId;

  private String message;

  private String createdTimestamp;
}
