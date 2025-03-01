package com.radz.wfh.controller;

import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.dto.*;
import com.radz.wfh.service.EmployeeDetailService;
import com.radz.wfh.service.WfhDetailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/wfh")
public class WfhController {

  private final WfhDetailService wfhDetailService;
  private final EmployeeDetailService employeeDetailService;

  public WfhController(
      WfhDetailService wfhDetailService, EmployeeDetailService employeeDetailService) {
    this.wfhDetailService = wfhDetailService;
    this.employeeDetailService = employeeDetailService;
  }

  @PostMapping("/requestWfh")
  public ResponseEntity<?> requestWfh(@Valid @RequestBody EmployeeWfhData employeeWfhData) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    WfhResponse wfhResponse =
        wfhDetailService.requestWfh(authentication.getName(), employeeWfhData);
    return new ResponseEntity<>(wfhResponse, HttpStatus.OK);
  }

  @GetMapping("/getEmployeeWfhDetail")
  public ResponseEntity<?> getEmployeeWfhDetail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return new ResponseEntity<>(
        wfhDetailService.getEmployeeWfhDetail(authentication.getName()), HttpStatus.OK);
  }

  @GetMapping("/getEmployeeWfhBalance")
  public ResponseEntity<?> getEmployeeWfhBalance() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return new ResponseEntity<>(
        wfhDetailService.getEmployeeWfhBalance(authentication.getName()), HttpStatus.OK);
  }

  @GetMapping("/getPendingEmployeeRegistration")
  public ResponseEntity<?> getPendingEmployeeRegistration() {
    return new ResponseEntity<>(
        employeeDetailService.getPendingRegisterRequestList(), HttpStatus.OK);
  }

  @GetMapping("/getManagers")
  public ResponseEntity<?> getManagers() {
    return new ResponseEntity<>(employeeDetailService.getManagerDetails(), HttpStatus.OK);
  }

  @GetMapping("/getEmployeePendingWfhRequests")
  public ResponseEntity<?> getEmployeePendingWfhRequests() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return new ResponseEntity<>(
        wfhDetailService.getEmployeePendingWfhRequests(authentication.getName()), HttpStatus.OK);
  }

  @PostMapping("/updateEmployeeData")
  public ResponseEntity<?> updateEmployeeData(
      @Valid @RequestBody EmployeeDetailData employeeDetailData) {

    boolean isUpdateSuccessful = employeeDetailService.updateEmployeeDetail(employeeDetailData);
    return new ResponseEntity<>(isUpdateSuccessful, HttpStatus.OK);
  }

  @GetMapping("/getEmployeeData")
  public ResponseEntity<?> getEmployeeDetail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return new ResponseEntity<>(
        employeeDetailService.getEmployeeDetail(authentication.getName()), HttpStatus.OK);
  }

  @PostMapping("/updateWfhRequestStatus")
  public ResponseEntity<?> updateWfhRequestStatus(
      @RequestParam("wfhRequestId") Long wfhRequestId,
      @RequestParam("status") WfhRequestStatus status) {
      return new ResponseEntity<>(
        wfhDetailService.updateEmployeeWfhRequest(wfhRequestId, status), HttpStatus.OK);
  }
}
