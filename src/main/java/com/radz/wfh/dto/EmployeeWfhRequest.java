package com.radz.wfh.dto;

import com.radz.wfh.constant.WfhType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeWfhRequest {
    private Long employeeId;
    private WfhType wfhType;
    private LocalDate requestedWfhDate;
}
