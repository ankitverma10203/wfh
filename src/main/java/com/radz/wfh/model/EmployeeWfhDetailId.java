package com.radz.wfh.model;

import com.radz.wfh.constant.WfhType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class EmployeeWfhDetailId {

    Long employeeId;
    WfhType wfhType;
}
