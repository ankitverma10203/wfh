package com.radz.wfh.controller;

import com.radz.wfh.constant.Role;
import com.radz.wfh.model.EmployeeDetail;
import com.radz.wfh.service.WfhDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wfh")
public class WfhController {

    private final WfhDetailService wfhDetailService;

    public WfhController(WfhDetailService wfhDetailService) {
        this.wfhDetailService = wfhDetailService;
    }

    @GetMapping("/getEmployeeWfhDetail/{employeeId}")
    public ResponseEntity<?> getEmployeeWfhDetail(@PathVariable("employeeId") Long employeeId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/saveEmployeeDetail/{name}/{role}")
    public ResponseEntity<EmployeeDetail> saveEmployeeDetail(@PathVariable("name") String name, @PathVariable("role") Role role) {

        EmployeeDetail employeeDetail = wfhDetailService.saveEmployeeDetail(name, role);
        return new ResponseEntity<>(employeeDetail, HttpStatus.OK);
    }
}
