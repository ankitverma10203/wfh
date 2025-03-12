package com.radz.wfh.constant;

import lombok.Getter;

@Getter
public enum NotificationType {
  APPROVAL_REQUEST_REGISTRATION("Employee Registration Approval Request"),
  APPROVAL_REQUEST_WFH("Employee WFH Approval Request"),
  UPDATE_EMPLOYEE_DETAIL("Employee Details Updated"),
  UPDATE_WFH_REQUEST("WFH Request Status Updated");

  private final String message;

  NotificationType(String message) {
    this.message = message;
  }
}
