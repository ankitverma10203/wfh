package com.radz.wfh.controller;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.EmployeeDetailRequest;
import com.radz.wfh.dto.EmployeeWfhRequest;
import com.radz.wfh.service.EmployeeRegistrationService;
import com.radz.wfh.service.WfhDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wfh")
public class WfhController {

  private final WfhDetailService wfhDetailService;
  private final EmployeeRegistrationService employeeRegistrationService;

  public WfhController(
      WfhDetailService wfhDetailService, EmployeeRegistrationService employeeRegistrationService) {
    this.wfhDetailService = wfhDetailService;
    this.employeeRegistrationService = employeeRegistrationService;
  }

  @GetMapping("/getEmployeeWfhDetail/{employeeId}")
  public ResponseEntity<?> getEmployeeWfhDetail(@PathVariable("employeeId") Long employeeId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody EmployeeDetailRequest employeeDetail) {

    EmployeeStatus registerStatus = employeeRegistrationService.register(employeeDetail);
    return new ResponseEntity<>(registerStatus, HttpStatus.OK);
  }

  @PostMapping("/requestWfh")
  public ResponseEntity<?> requestWfh(@RequestBody EmployeeWfhRequest employeeWfhRequest) {

    boolean req = wfhDetailService.requestWfh(employeeWfhRequest);
    return new ResponseEntity<>(req, HttpStatus.OK);
  }
}
