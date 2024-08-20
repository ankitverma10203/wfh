package com.radz.wfh.service.impl;

import com.radz.wfh.constant.WfhType;
import com.radz.wfh.model.WfhQuantityRef;
import com.radz.wfh.repository.WfhQuantityRefRepository;
import com.radz.wfh.service.WfhQuantityRefService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WfhQuantityRefServiceImpl implements WfhQuantityRefService, CommandLineRunner {

  private Map<WfhType, Long> quantityByWfhTypeMap;
  private final WfhQuantityRefRepository wfhQuantityRefRepository;

  public WfhQuantityRefServiceImpl(WfhQuantityRefRepository wfhQuantityRefRepository) {
    this.wfhQuantityRefRepository = wfhQuantityRefRepository;
  }

  private void init() {
    List<WfhQuantityRef> wfhQuantityRefData = wfhQuantityRefRepository.findAll();
    this.quantityByWfhTypeMap =
        Collections.unmodifiableMap(
            wfhQuantityRefData.stream()
                .collect(
                    Collectors.toMap(WfhQuantityRef::getWfhType, WfhQuantityRef::getQuantity)));
  }

  @Override
  public Map<WfhType, Long> getQuantityByWfhTypeMap() {
    return quantityByWfhTypeMap;
  }

  @Override
  public void run(String... args) throws Exception {
    init();
  }
}
