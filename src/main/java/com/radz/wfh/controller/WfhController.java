package com.radz.wfh.controller;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.EmployeeRegistrationRequest;
import com.radz.wfh.dto.EmployeeWfhData;
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
  public ResponseEntity<?> register(@Valid @RequestBody EmployeeRegistrationRequest employeeDetail) {

    EmployeeStatus registerStatus = employeeRegistrationService.register(employeeDetail);
    return new ResponseEntity<>(registerStatus, HttpStatus.OK);
  }

  @PostMapping("/requestWfh")
  public ResponseEntity<?> requestWfh(@Valid @RequestBody EmployeeWfhData employeeWfhData) {

    boolean req = wfhDetailService.requestWfh(employeeWfhData);
    return new ResponseEntity<>(req, HttpStatus.OK);
  }
}
