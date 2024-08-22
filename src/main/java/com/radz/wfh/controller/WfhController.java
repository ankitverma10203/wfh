package com.radz.wfh.controller;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.*;
import com.radz.wfh.service.EmployeeDetailService;
import com.radz.wfh.service.EmployeeLoginService;
import com.radz.wfh.service.EmployeeRegistrationService;
import com.radz.wfh.service.WfhDetailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/wfh")
public class WfhController {

  private final WfhDetailService wfhDetailService;
  private final EmployeeRegistrationService employeeRegistrationService;
  private final EmployeeLoginService employeeLoginService;
  private final EmployeeDetailService employeeDetailService;

  public WfhController(
      WfhDetailService wfhDetailService,
      EmployeeRegistrationService employeeRegistrationService,
      EmployeeLoginService employeeLoginService,
      EmployeeDetailService employeeDetailService) {
    this.wfhDetailService = wfhDetailService;
    this.employeeRegistrationService = employeeRegistrationService;
    this.employeeLoginService = employeeLoginService;
    this.employeeDetailService = employeeDetailService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(
      @Valid @RequestBody EmployeeRegistrationRequest employeeDetail) {

    EmployeeStatus registerStatus = employeeRegistrationService.register(employeeDetail);
    return new ResponseEntity<>(registerStatus, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody EmployeeLoginRequest employeeLoginRequest) {

    LoginResponse loginResponse = employeeLoginService.login(employeeLoginRequest);
    return new ResponseEntity<>(loginResponse, HttpStatus.OK);
  }

  @PostMapping("/requestWfh")
  public ResponseEntity<?> requestWfh(@Valid @RequestBody EmployeeWfhData employeeWfhData) {

    WfhResponse wfhResponse = wfhDetailService.requestWfh(employeeWfhData);
    return new ResponseEntity<>(wfhResponse, HttpStatus.OK);
  }

  @GetMapping("/getEmployeeWfhDetail/{employeeId}")
  public ResponseEntity<?> getEmployeeWfhDetail(@PathVariable("employeeId") Long employeeId) {
    return new ResponseEntity<>(wfhDetailService.getEmployeeWfhDetail(employeeId), HttpStatus.OK);
  }

  @GetMapping("/getEmployeeWfhBalance/{employeeId}")
  public ResponseEntity<?> getEmployeeWfhBalance(@PathVariable("employeeId") Long employeeId) {
    return new ResponseEntity<>(wfhDetailService.getEmployeeWfhBalance(employeeId), HttpStatus.OK);
  }

  @GetMapping("/getPendingEmployeeRegistration")
  public ResponseEntity<?> getEmployeeWfhBalance() {
    return new ResponseEntity<>(
        employeeDetailService.getPendingRegisterRequestList(), HttpStatus.OK);
  }

  @GetMapping("/getManagers")
  public ResponseEntity<?> getManagers() {
    return new ResponseEntity<>(employeeDetailService.getManagerDetails(), HttpStatus.OK);
  }

  @PostMapping("/updateEmployeeData")
  public ResponseEntity<?> requestWfh(@Valid @RequestBody EmployeeDetailData employeeDetailData) {

    boolean isUpdateSuccessful = employeeDetailService.updateEmployeeDetail(employeeDetailData);
    return new ResponseEntity<>(isUpdateSuccessful, HttpStatus.OK);
  }
}
