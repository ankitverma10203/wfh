package com.radz.wfh.service;

import com.radz.wfh.dto.EmployeeNotificationData;
import reactor.core.publisher.Sinks;

import java.util.List;

public interface NotificationService {
  Sinks.Many<EmployeeNotificationData> getSink(String employeeId);

  void removeSink(String employeeId);

  void saveAndPushNotification(String employeeId, String message);

  List<EmployeeNotificationData> getNotifications(String employeeId);

  boolean clearNotifications(String employeeId, List<Long> notificationIds);
}
