package com.radz.wfh.service.impl;

import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.constant.WfhType;
import com.radz.wfh.dto.EmployeeWfhData;
import com.radz.wfh.dto.EmployeeWfhDetailData;
import com.radz.wfh.dto.WfhBalanceInfo;
import com.radz.wfh.dto.WfhResponse;
import com.radz.wfh.model.EmployeeWfhDetail;
import com.radz.wfh.repository.EmployeeWfhDetailRepository;
import com.radz.wfh.service.EmployeeDetailService;
import com.radz.wfh.service.WfhDetailService;
import com.radz.wfh.service.WfhQuantityRefService;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WfhDetailServiceImpl implements WfhDetailService {

  private final EmployeeWfhDetailRepository employeeWfhDetailRepository;
  private final WfhQuantityRefService wfhQuantityRefService;
  private final EmployeeDetailService employeeDetailService;

  public WfhDetailServiceImpl(
      EmployeeWfhDetailRepository employeeWfhDetailRepository,
      WfhQuantityRefService wfhQuantityRefService,
      EmployeeDetailService employeeDetailService) {
    this.employeeWfhDetailRepository = employeeWfhDetailRepository;
    this.wfhQuantityRefService = wfhQuantityRefService;
    this.employeeDetailService = employeeDetailService;
  }

  @Override
  public WfhResponse requestWfh(EmployeeWfhData employeeWfhData) {

    Optional<WfhRequestStatus> optionalWfhRequestStatus = validateWfhRequest(employeeWfhData);

    if (optionalWfhRequestStatus.isPresent()) {
      return WfhResponse.builder().status(optionalWfhRequestStatus.get()).build();
    }

    WfhRequestStatus wfhRequestStatus = WfhRequestStatus.PENDING_APPROVAL;
    EmployeeWfhDetail employeeWfhDetail =
        EmployeeWfhDetail.builder()
            .employeeId(employeeWfhData.getEmployeeId())
            .wfhType(employeeWfhData.getWfhType())
            .status(wfhRequestStatus)
            .requestedWfhDate(employeeWfhData.getRequestedWfhDate())
            .build();

    employeeWfhDetailRepository.save(employeeWfhDetail);
    return WfhResponse.builder().successFlg(true).status(wfhRequestStatus).build();
  }

  private Optional<WfhRequestStatus> validateWfhRequest(EmployeeWfhData employeeWfhData) {

    if (employeeDetailService
        .validateRequestedId(employeeWfhData.getEmployeeId().toString())
        .isEmpty()) {
      return Optional.of(WfhRequestStatus.INVALID_REQUEST);
    }

    List<EmployeeWfhDetail> employeeWfhDetailList =
        employeeWfhDetailRepository.findByEmployeeId(employeeWfhData.getEmployeeId());

    if (isWfhForSameDayExist(employeeWfhData.getRequestedWfhDate(), employeeWfhDetailList)) {
      return Optional.of(WfhRequestStatus.DUPLICATE_REQUEST);
    }
    if (isWfhExhausted(employeeWfhData, employeeWfhDetailList)) {
      return Optional.of(WfhRequestStatus.INSUFFICIENT_WFH);
    }

    return Optional.empty();
  }

  private boolean isWfhExhausted(
      EmployeeWfhData employeeWfhData, List<EmployeeWfhDetail> employeeWfhDetailList) {

    long countOfWfhAvailedForRequestedWfhType =
        employeeWfhDetailList.stream()
            .filter(
                employeeWfhDetail ->
                    employeeWfhDetail.getWfhType().equals(employeeWfhData.getWfhType())
                        && Arrays.asList(
                                WfhRequestStatus.PENDING_APPROVAL, WfhRequestStatus.APPROVED)
                            .contains(employeeWfhDetail.getStatus()))
            .count();

    return wfhQuantityRefService.getQuantityByWfhTypeMap().get(employeeWfhData.getWfhType())
        < countOfWfhAvailedForRequestedWfhType;
  }

  private boolean isWfhForSameDayExist(
      LocalDate requestedWfhDate, List<EmployeeWfhDetail> employeeWfhDetailList) {
    return employeeWfhDetailList.stream()
        .map(EmployeeWfhDetail::getRequestedWfhDate)
        .anyMatch(requestedWfhDate::equals);
  }

  @Override
  public List<EmployeeWfhDetailData> getEmployeeWfhDetail(Long employeeId) {
    List<EmployeeWfhDetail> employeeWfhDetailList =
        employeeWfhDetailRepository.findByEmployeeId(employeeId);

    return employeeWfhDetailList.stream()
        .map(
            employeeWfhDetail ->
                EmployeeWfhDetailData.builder()
                    .wfhType(employeeWfhDetail.getWfhType())
                    .requestedWfhDate(employeeWfhDetail.getRequestedWfhDate())
                    .status(employeeWfhDetail.getStatus())
                    .createdTimestamp(employeeWfhDetail.getCreatedTimestamp())
                    .updatedTimestamp(employeeWfhDetail.getUpdatedTimestamp())
                    .build())
        .toList();
  }

  @Override
  public WfhBalanceInfo getEmployeeWfhBalance(Long employeeId) {
    Map<WfhType, Long> quantityByWfhTypeMap = wfhQuantityRefService.getQuantityByWfhTypeMap();

    List<EmployeeWfhDetail> employeeWfhDetailList =
        employeeWfhDetailRepository.findByEmployeeId(employeeId);
    Map<WfhType, Long> wfhAvailedByWfhTypeMap = getWfhAvailedByWfhTypeMap(employeeWfhDetailList);

    Map<WfhType, Long> wfhPendingByWfhTypeMap = getWfhPendingByWfhTypeMap(employeeWfhDetailList);

    Map<WfhType, Long> RemainingWfhByWfhTypeMap =
        getWfhRemainingByWfhTypeMap(
            quantityByWfhTypeMap, wfhAvailedByWfhTypeMap, wfhPendingByWfhTypeMap);

    return WfhBalanceInfo.builder()
        .remainingWfhQuantityMap(RemainingWfhByWfhTypeMap)
        .availedWfhQuantityMap(wfhAvailedByWfhTypeMap)
        .pendingWfhQuantityMap(wfhPendingByWfhTypeMap)
        .build();
  }

  private Map<WfhType, Long> getWfhRemainingByWfhTypeMap(
      Map<WfhType, Long> quantityByWfhTypeMap,
      Map<WfhType, Long> wfhAvailedByWfhTypeMap,
      Map<WfhType, Long> wfhPendingByWfhTypeMap) {
    Map<WfhType, Long> wfhRemainingByWfhType =
        quantityByWfhTypeMap.keySet().stream()
            .collect(
                Collectors.toMap(
                    wfhType -> wfhType,
                    wfhType ->
                        quantityByWfhTypeMap.getOrDefault(wfhType, 0L)
                            - wfhAvailedByWfhTypeMap.getOrDefault(wfhType, 0L)
                            - wfhPendingByWfhTypeMap.getOrDefault(wfhType, 0L)));

    return addMissingWfhTypeInfoToMap(wfhRemainingByWfhType);
  }

  private Map<WfhType, Long> getWfhPendingByWfhTypeMap(
      List<EmployeeWfhDetail> employeeWfhDetailList) {
    Map<WfhType, Long> wfhPendingByWfhTypeMap =
        employeeWfhDetailList.stream()
            .filter(
                employeeWfhDetail ->
                    Objects.equals(
                        WfhRequestStatus.PENDING_APPROVAL, employeeWfhDetail.getStatus()))
            .collect(Collectors.groupingBy(EmployeeWfhDetail::getWfhType, Collectors.counting()));

    return addMissingWfhTypeInfoToMap(wfhPendingByWfhTypeMap);
  }

  private Map<WfhType, Long> getWfhAvailedByWfhTypeMap(
      List<EmployeeWfhDetail> employeeWfhDetailList) {
    Map<WfhType, Long> wfhAvailedByWfhTypeMap =
        employeeWfhDetailList.stream()
            .filter(
                employeeWfhDetail ->
                    Objects.equals(WfhRequestStatus.APPROVED, employeeWfhDetail.getStatus()))
            .collect(Collectors.groupingBy(EmployeeWfhDetail::getWfhType, Collectors.counting()));
    return addMissingWfhTypeInfoToMap(wfhAvailedByWfhTypeMap);
  }

  private Map<WfhType, Long> addMissingWfhTypeInfoToMap(Map<WfhType, Long> wfhTypeByCountMap) {
    Arrays.stream(WfhType.values()).forEach(wfhType -> wfhTypeByCountMap.putIfAbsent(wfhType, 0L));
    return wfhTypeByCountMap;
  }
}
