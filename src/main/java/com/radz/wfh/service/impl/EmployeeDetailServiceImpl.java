package com.radz.wfh.service.impl;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.constant.NotificationType;
import com.radz.wfh.constant.Role;
import com.radz.wfh.dto.EmployeeDetailData;
import com.radz.wfh.model.EmployeeDetail;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.EmployeeDetailService;
import com.radz.wfh.service.NotificationService;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeDetailServiceImpl implements EmployeeDetailService {

  private final EmployeeDetailRepository employeeDetailRepository;
  private final NotificationService notificationService;

  public EmployeeDetailServiceImpl(
      EmployeeDetailRepository employeeDetailRepository, NotificationService notificationService) {
    this.employeeDetailRepository = employeeDetailRepository;
    this.notificationService = notificationService;
  }

  @Override
  public List<EmployeeDetailData> getPendingRegisterRequestList() {
    List<EmployeeDetail> pendingRegistrationList =
        employeeDetailRepository.findByStatus(EmployeeStatus.PENDING_APPROVAL);

    return createEmployeeDetailDataList(pendingRegistrationList);
  }

  @Override
  public List<EmployeeDetailData> getAllEmployeesDetail() {
    List<EmployeeDetail> pendingRegistrationList = employeeDetailRepository.findAll();

    return createEmployeeDetailDataList(pendingRegistrationList);
  }

  @Override
  public List<EmployeeDetailData> getManagerDetails() {

    List<EmployeeDetail> managerDetails =
        employeeDetailRepository.getEmployeesByRoleAndStatus(Role.MANAGER, EmployeeStatus.ACTIVE);

    return createEmployeeDetailDataList(managerDetails);
  }

  @Override
  public List<EmployeeDetailData> getAdminDetails() {
    List<EmployeeDetail> managerDetails =
        employeeDetailRepository.getEmployeesByRoleAndStatus(Role.ADMIN, EmployeeStatus.ACTIVE);

    return createEmployeeDetailDataList(managerDetails);
  }

  private List<EmployeeDetailData> createEmployeeDetailDataList(
      List<EmployeeDetail> managerDetails) {
    return managerDetails.stream()
        .map(
            managerDetail ->
                EmployeeDetailData.builder()
                    .employeeId(managerDetail.getEmployeeId())
                    .employeeStatus(managerDetail.getStatus())
                    .name(managerDetail.getName())
                    .email(managerDetail.getEmail())
                    .managerId(managerDetail.getManagerId())
                    .role(managerDetail.getRole())
                    .build())
        .toList();
  }

  @Override
  public boolean updateEmployeeDetail(EmployeeDetailData employeeDetailData) {

    Optional<EmployeeDetail> optionalEmployeeDetail =
        employeeDetailRepository.findById(employeeDetailData.getEmployeeId());

    if (optionalEmployeeDetail.isEmpty()) {
      return false;
    }

    EmployeeDetail employeeDetail = optionalEmployeeDetail.get();
    employeeDetail.setStatus(employeeDetailData.getEmployeeStatus());
    employeeDetail.setManagerId(employeeDetailData.getManagerId());
    employeeDetail.setRole(employeeDetailData.getRole());
    employeeDetailRepository.save(employeeDetail);

    String notificationMessage =
        MessageFormat.format(
            NotificationType.UPDATE_EMPLOYEE_DETAIL.getMessage()
                + ": Status: {0}, managerId: {1}, role: {2}",
            employeeDetail.getStatus(),
            employeeDetail.getManagerId(),
            employeeDetail.getRole());
    notificationService.saveAndPushNotification(
        employeeDetail.getEmployeeId(), notificationMessage);
    return true;
  }

  @Override
  public EmployeeDetailData getEmployeeDetail(String requestedId) {
    Optional<EmployeeDetail> employeeDetailOptional =
        employeeDetailRepository.findById(requestedId);

    return employeeDetailOptional
        .map(
            employeeDetail ->
                EmployeeDetailData.builder()
                    .employeeId(employeeDetail.getEmployeeId())
                    .employeeStatus(employeeDetail.getStatus())
                    .name(employeeDetail.getName())
                    .email(employeeDetail.getEmail())
                    .managerId(employeeDetail.getManagerId())
                    .role(employeeDetail.getRole())
                    .build())
        .orElse(EmployeeDetailData.builder().build());
  }

  @Override
  public String getManagerForEmployee(String employeeId) {
    Optional<EmployeeDetail> employeeDetailOptional = employeeDetailRepository.findById(employeeId);

    return employeeDetailOptional.map(EmployeeDetail::getManagerId).orElse(null);
  }
}
