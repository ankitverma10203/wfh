package com.radz.wfh.model;

import com.radz.wfh.constant.WfhType;
import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EmployeeWfhDetailId {

  private Long employeeId;
  private WfhType wfhType;
  private LocalDate requestedWfhDate;
}
