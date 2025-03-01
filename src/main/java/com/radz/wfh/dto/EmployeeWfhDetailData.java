package com.radz.wfh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.constant.WfhType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeWfhDetailData {

  @JsonProperty("id")
  private Long wfhRequestId;

  private WfhType wfhType;

  private String requestedWfhDate;

  private WfhRequestStatus status;

  private String createdTimestamp;

  private String updatedTimestamp;
}
