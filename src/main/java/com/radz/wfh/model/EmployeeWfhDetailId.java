package com.radz.wfh.model;

import com.radz.wfh.constant.WfhType;
import java.time.LocalDate;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWfhDetailId {

  private Long employeeId;
  private WfhType wfhType;
  private LocalDate requestedWfhDate;
}
