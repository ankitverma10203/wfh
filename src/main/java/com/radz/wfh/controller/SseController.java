package com.radz.wfh.controller;

import com.radz.wfh.dto.EmployeeNotificationData;
import com.radz.wfh.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
@RestController
public class SseController {

  private final NotificationService notificationService;

  public SseController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping(value = "/notification/{employeeId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<EmployeeNotificationData> sse(@PathVariable("employeeId") String employeeId) {
    Sinks.Many<EmployeeNotificationData> sink = notificationService.getSink(employeeId);
    return sink.asFlux()
        .doOnError(
            e -> {
              if (e instanceof java.io.IOException) {
                log.error("Client disconnected.");
              } else {
                log.error("Exception while pushing the notification.", e);
              }
            })
        .doOnCancel(() -> {
            sink.tryEmitComplete().orThrow();
            notificationService.removeSink(employeeId);
        });
  }
}
