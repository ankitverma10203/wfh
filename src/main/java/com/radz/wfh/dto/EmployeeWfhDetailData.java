package com.radz.wfh.dto;

import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.constant.WfhType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeWfhDetailData {

  private WfhType wfhType;

  private LocalDate requestedWfhDate;

  private WfhRequestStatus status;

  private LocalDateTime createdTimestamp;

  private LocalDateTime updatedTimestamp;
}
