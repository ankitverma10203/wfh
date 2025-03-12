package com.radz.wfh.service.impl;

import com.radz.wfh.constant.NotificationType;
import com.radz.wfh.dto.EmployeeNotificationData;
import com.radz.wfh.model.EmployeeNotificationDetail;
import com.radz.wfh.repository.EmployeeNotificationRepository;
import com.radz.wfh.service.NotificationService;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
  private final ConcurrentHashMap<String, Sinks.Many<EmployeeNotificationData>> userSinks =
      new ConcurrentHashMap<>();
  private final EmployeeNotificationRepository employeeNotificationRepository;

  public NotificationServiceImpl(EmployeeNotificationRepository employeeNotificationRepository) {
    this.employeeNotificationRepository = employeeNotificationRepository;
  }

  @Override
  public Sinks.Many<EmployeeNotificationData> getSink(String employeeId) {
    userSinks.computeIfAbsent(employeeId, k -> Sinks.many().multicast().onBackpressureBuffer());
    return userSinks.get(employeeId);
  }

  @Override
  public void removeSink(String employeeId) {
    userSinks.remove(employeeId);
  }

  private void pushNotification(
      String employeeId, EmployeeNotificationData employeeNotificationData) {
    getSink(employeeId).tryEmitNext(employeeNotificationData);
    log.info("Notification sent");
  }

  @Override
  public void saveAndPushNotification(
      String employeeId, String message, NotificationType notificationType) {
    EmployeeNotificationDetail employeeNotificationDetail =
        EmployeeNotificationDetail.builder()
            .employeeId(employeeId)
            .message(message)
            .notificationType(notificationType)
            .build();

    employeeNotificationRepository.save(employeeNotificationDetail);

//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm:ss");
    EmployeeNotificationData employeeNotificationData =
        EmployeeNotificationData.builder()
            .notificationId(employeeNotificationDetail.getNotificationId())
            .message(employeeNotificationDetail.getMessage())
            .notificationType(employeeNotificationDetail.getNotificationType())
            // .createdTimestamp(employeeNotificationDetail.getCreatedTimestamp().format(formatter))
            .build();

    pushNotification(employeeId, employeeNotificationData);
  }

  @Override
  public List<EmployeeNotificationData> getNotifications(String employeeId) {
    List<EmployeeNotificationDetail> employeeNotificationDetails =
        employeeNotificationRepository.findByEmployeeId(employeeId);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm:ss");
    return employeeNotificationDetails.stream()
        .map(
            employeeNotificationDetail ->
                EmployeeNotificationData.builder()
                    .notificationId(employeeNotificationDetail.getNotificationId())
                    .message(employeeNotificationDetail.getMessage())
                    .createdTimestamp(
                        employeeNotificationDetail.getCreatedTimestamp().format(formatter))
                    .build())
        .toList();
  }

  @Override
  public boolean clearNotifications(String employeeId, List<Long> notificationIds) {
    employeeNotificationRepository.deleteAllById(notificationIds);
    return true;
  }
}
