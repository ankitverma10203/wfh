package com.radz.wfh.service.impl;

import com.radz.wfh.constant.Role;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.EmployeeDetailService;
import lombok.extern.slf4j.Slf4j;
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
}
