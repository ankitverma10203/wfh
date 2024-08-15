package com.radz.wfh.service.impl;

import com.radz.wfh.constant.Role;
import com.radz.wfh.model.EmployeeDetail;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.WfhDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WfhDetailServiceImpl implements WfhDetailService {

    private final EmployeeDetailRepository employeeDetailRepository;

    public WfhDetailServiceImpl(EmployeeDetailRepository employeeDetailRepository) {
        this.employeeDetailRepository = employeeDetailRepository;
    }


    @Override
    public EmployeeDetail saveEmployeeDetail(String name, Role role) {

        EmployeeDetail employeeDetail = EmployeeDetail.builder().name(name).role(role).createdBy("APP").build();

        return employeeDetailRepository.save(employeeDetail);
    }
}
