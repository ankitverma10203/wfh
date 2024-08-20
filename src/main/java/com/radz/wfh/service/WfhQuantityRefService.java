package com.radz.wfh.service;

import com.radz.wfh.constant.WfhType;

import java.util.Map;

public interface WfhQuantityRefService {
    Map<WfhType, Long> getQuantityByWfhTypeMap();
}
