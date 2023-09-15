package com.example.demo.service;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.dto.DepartmentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    public List<DepartmentDTO> getDepartmentHierarchy(String CO_CD) {
        try {
            List<DepartmentDTO> departments = departmentDao.getDepartmentHierarchy(CO_CD);
            log.info("Get Department List Service", CO_CD);
            return departments;
        } catch (Exception e) {
            log.error("Error while fetching department hierarchy for DIV_CD: {}", CO_CD, e);
            return Collections.emptyList();
        }
    }
}
