package com.radz.wfh.service.impl;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.constant.Role;
import com.radz.wfh.dto.EmployeeDetailData;
import com.radz.wfh.dto.EmployeeInfo;
import com.radz.wfh.model.EmployeeDetail;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.EmployeeDetailService;
import com.radz.wfh.service.EmployeeRegistrationService;
import com.radz.wfh.service.NotificationService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeRegistrationServiceImpl implements EmployeeRegistrationService {

  private final EmployeeDetailRepository employeeDetailRepository;
  private final NotificationService notificationService;
  private final EmployeeDetailService employeeDetailService;

  public EmployeeRegistrationServiceImpl(
      EmployeeDetailRepository employeeDetailRepository,
      NotificationService notificationService,
      EmployeeDetailService employeeDetailService) {
    this.employeeDetailRepository = employeeDetailRepository;
    this.notificationService = notificationService;
    this.employeeDetailService = employeeDetailService;
  }

  @Transactional
  @Override
  public EmployeeStatus register(EmployeeInfo employeeInfo) {
    boolean isEmployeeDetailTableEmpty = employeeDetailRepository.count() == 0;

    EmployeeStatus status =
        isEmployeeDetailTableEmpty ? EmployeeStatus.ACTIVE : EmployeeStatus.PENDING_APPROVAL;
    Role role = isEmployeeDetailTableEmpty ? Role.ADMIN : Role.EMPLOYEE;
    String managerId = isEmployeeDetailTableEmpty ? employeeInfo.getSub() : "0";

    EmployeeDetail employeeDetail =
        EmployeeDetail.builder()
            .employeeId(employeeInfo.getSub())
            .name(employeeInfo.getName())
            .email(employeeInfo.getEmail())
            .status(status)
            .role(role)
            .managerId(managerId)
            .build();

    employeeDetailRepository.save(employeeDetail);

    List<EmployeeDetailData> adminDetails = employeeDetailService.getAdminDetails();
    String notificationMessage =
        String.format(
            "Employee Registered: employee id:%s, name:%s, email:%s",
            employeeDetail.getEmployeeId(), employeeDetail.getName(), employeeDetail.getEmail());

    adminDetails.forEach(
        employeeDetailData -> {
          notificationService.saveAndPushNotification(
              employeeDetailData.getEmployeeId(), notificationMessage);
        });

    return status;
  }
}
