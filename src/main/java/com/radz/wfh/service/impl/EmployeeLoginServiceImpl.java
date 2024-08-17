package com.radz.wfh.service.impl;

import com.radz.wfh.constant.LoginStatus;
import com.radz.wfh.dto.EmployeeLoginRequest;
import com.radz.wfh.model.EmployeeCredential;
import com.radz.wfh.repository.EmployeeCredentialRepository;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.EmployeeLoginService;
import com.radz.wfh.utility.WfhUtility;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeLoginServiceImpl implements EmployeeLoginService {

  private final EmployeeCredentialRepository employeeCredentialRepository;
  private final EmployeeDetailRepository employeeDetailRepository;

  public EmployeeLoginServiceImpl(
      EmployeeCredentialRepository employeeCredentialRepository,
      EmployeeDetailRepository employeeDetailRepository) {
    this.employeeCredentialRepository = employeeCredentialRepository;
    this.employeeDetailRepository = employeeDetailRepository;
  }

  @Override
  public LoginStatus login(EmployeeLoginRequest employeeLoginRequest) {

    Optional<Long> employeeId = validateRequestedId(employeeLoginRequest.getId());

    if (employeeId.isEmpty()) {
      return LoginStatus.USER_NOT_FOUND;
    }

    Optional<EmployeeCredential> optionalEmployeeCredential =
        employeeCredentialRepository.findById(employeeId.get());

    EmployeeCredential employeeCredential = optionalEmployeeCredential.get();

    return WfhUtility.validatePassword(
            employeeLoginRequest.getPassword(), employeeCredential.getPassword())
        ? LoginStatus.LOGIN_SUCCESS
        : LoginStatus.INCORRECT_PASSWORD;
  }

  private Optional<Long> validateRequestedId(String requestedId) {
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
