package com.radz.wfh.service.impl;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.EmployeeDetailData;
import com.radz.wfh.model.EmployeeDetail;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.EmployeeDetailService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeDetailServiceImpl implements EmployeeDetailService {

  private final EmployeeDetailRepository employeeDetailRepository;

    public EmployeeDetailServiceImpl(
      EmployeeDetailRepository employeeDetailRepository) {
    this.employeeDetailRepository = employeeDetailRepository;
    }

  @Override
  public List<EmployeeDetailData> getPendingRegisterRequestList() {
    List<EmployeeDetail> pendingRegistrationList =
        employeeDetailRepository.findByStatus(EmployeeStatus.PENDING_APPROVAL);

    return pendingRegistrationList.stream()
        .map(
            pendingRegistration ->
                EmployeeDetailData.builder()
                    .employeeId(pendingRegistration.getEmployeeId())
                    .employeeStatus(pendingRegistration.getStatus())
                    .name(pendingRegistration.getName())
                    .email(pendingRegistration.getEmail())
                    .managerId(pendingRegistration.getManagerId())
                    .build())
        .toList();
  }

  @Override
  public List<EmployeeDetailData> getManagerDetails() {
    List<EmployeeDetail> managerDetails = new ArrayList<>();
    //        employeeDetailRepository.findByStatusAndRole(EmployeeStatus.ACTIVE, Role.MANAGER);

    return managerDetails.stream()
        .map(
            managerDetail ->
                EmployeeDetailData.builder()
                    .employeeId(managerDetail.getEmployeeId())
                    .employeeStatus(managerDetail.getStatus())
                    .name(managerDetail.getName())
                    .email(managerDetail.getEmail())
                    .managerId(managerDetail.getManagerId())
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
    employeeDetailRepository.save(employeeDetail);
    return true;
  }

  @Override
  public Optional<EmployeeDetail> getEmployeeDetail(String requestedId) {
    return employeeDetailRepository.findById(requestedId);
  }
}
