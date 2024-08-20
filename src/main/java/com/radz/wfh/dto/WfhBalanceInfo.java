package com.radz.wfh.dto;

import com.radz.wfh.constant.WfhType;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WfhBalanceInfo {
  Map<WfhType, Long> remainingWfhQuantityMap;
  Map<WfhType, Long> availedWfhQuantityMap;
  Map<WfhType, Long> pendingWfhQuantityMap;
}
