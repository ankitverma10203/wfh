package com.radz.wfh.service.impl;

import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.constant.WfhType;
import com.radz.wfh.dto.EmployeeWfhRequest;
import com.radz.wfh.model.EmployeeWfhDetail;
import com.radz.wfh.model.WfhQuantityRef;
import com.radz.wfh.repository.EmployeeWfhDetailRepository;
import com.radz.wfh.repository.WfhQuantityRefRepository;
import com.radz.wfh.service.WfhDetailService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WfhDetailServiceImpl implements WfhDetailService {

  private final EmployeeWfhDetailRepository employeeWfhDetailRepository;
  private final WfhQuantityRefRepository wfhQuantityRefRepository;

  public WfhDetailServiceImpl(
      EmployeeWfhDetailRepository employeeWfhDetailRepository,
      WfhQuantityRefRepository wfhQuantityRefRepository) {
    this.employeeWfhDetailRepository = employeeWfhDetailRepository;
    this.wfhQuantityRefRepository = wfhQuantityRefRepository;
  }

  @Override
  public boolean requestWfh(EmployeeWfhRequest employeeWfhRequest) {

    if (validateWfhRequest(employeeWfhRequest)) {
      return false;
    }

    EmployeeWfhDetail employeeWfhDetail =
        EmployeeWfhDetail.builder()
            .employeeId(employeeWfhRequest.getEmployeeId())
            .wfhType(employeeWfhRequest.getWfhType())
            .status(WfhRequestStatus.PENDING_APPROVAL)
            .requestedWfhDate(employeeWfhRequest.getRequestedWfhDate())
            .build();

    employeeWfhDetailRepository.save(employeeWfhDetail);
    return true;
  }

  private boolean validateWfhRequest(EmployeeWfhRequest employeeWfhRequest) {

    List<EmployeeWfhDetail> employeeWfhDetailList =
        employeeWfhDetailRepository.findByEmployeeIdAndWfhType(
            employeeWfhRequest.getEmployeeId(), employeeWfhRequest.getWfhType());

    return isWfhForSameDayExist(employeeWfhRequest.getRequestedWfhDate(), employeeWfhDetailList)
        || isWfhExhausted(employeeWfhRequest, employeeWfhDetailList);
  }

  private boolean isWfhExhausted(
      EmployeeWfhRequest employeeWfhRequest, List<EmployeeWfhDetail> employeeWfhDetailList) {
    List<WfhQuantityRef> wfhQuantityRefData = wfhQuantityRefRepository.findAll();
    Map<WfhType, Integer> quantityByWfhTypeMap =
        wfhQuantityRefData.stream()
            .collect(Collectors.toMap(WfhQuantityRef::getWfhType, WfhQuantityRef::getQuantity));

    return quantityByWfhTypeMap.get(employeeWfhRequest.getWfhType())
        > employeeWfhDetailList.stream()
            .filter(
                employeeWfhDetail ->
                    Arrays.asList(WfhRequestStatus.PENDING_APPROVAL, WfhRequestStatus.APPROVED)
                        .contains(employeeWfhDetail.getStatus()))
            .count();
  }

  private boolean isWfhForSameDayExist(
      LocalDate requestedWfhDate, List<EmployeeWfhDetail> employeeWfhDetailList) {
    return employeeWfhDetailList.stream()
        .map(EmployeeWfhDetail::getRequestedWfhDate)
        .anyMatch(requestedWfhDate::equals);
  }
}
