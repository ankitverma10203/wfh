package com.radz.wfh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.radz.wfh.constant.WfhType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeWfhData {
  @NotNull private Long employeeId;
  @NotNull private WfhType wfhType;

  @NotNull
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate requestedWfhDate;

  @NotNull
  private LocalDateTime requestedAt;
}
