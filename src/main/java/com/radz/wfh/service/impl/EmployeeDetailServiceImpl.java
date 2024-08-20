package com.radz.wfh.service.impl;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.constant.Role;
import com.radz.wfh.dto.EmployeeDetailData;
import com.radz.wfh.model.EmployeeDetail;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.EmployeeDetailService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeDetailServiceImpl implements EmployeeDetailService {

  private final EmployeeDetailRepository employeeDetailRepository;

  public EmployeeDetailServiceImpl(EmployeeDetailRepository employeeDetailRepository) {
    this.employeeDetailRepository = employeeDetailRepository;
  }

  @Override
  public boolean doesAdminExist() {
    int countOfAdmins = employeeDetailRepository.countByRole(Role.ADMIN);

    return countOfAdmins > 0;
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
                    .role(pendingRegistration.getRole())
                    .name(pendingRegistration.getName())
                    .build())
        .toList();
  }

  @Override
  public Optional<Long> validateRequestedId(String requestedId) {
    if (StringUtils.isNumeric(requestedId)) {
      Long employeeId = Long.parseLong(requestedId);
      return employeeDetailRepository.existsById(employeeId)
              ? Optional.of(employeeId)
              : Optional.empty();
    }

    Long employeeId = employeeDetailRepository.getEmployeeIdByEmail(requestedId);

    return Objects.nonNull(employeeId) ? Optional.of(employeeId) : Optional.empty();
  }
}
