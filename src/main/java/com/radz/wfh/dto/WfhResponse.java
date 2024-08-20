package com.radz.wfh.dto;

import com.radz.wfh.constant.WfhRequestStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WfhResponse {
    boolean successFlg;
    WfhRequestStatus status;
}
