package com.example.demo.service;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.WorkplaceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    public List<DepartmentDTO> getDepartmentHierarchy(String CO_CD) {
        try {
            List<DepartmentDTO> departments = departmentDao.getDepartmentHierarchy(CO_CD);
            log.info("Get Department List Service"+ CO_CD );
            return departments;
        } catch (Exception e) {
            log.error("Error while fetching department hierarchy for DIV_CD: {}", CO_CD, e);
            return Collections.emptyList();
        }
    }

    // 부서 상세
    public DepartmentDTO selectDepartmentInfoByDEPTCD(Map<String, String> params) {
        try {
            log.info("Get Department Detail Service"+ params );
            return departmentDao.selectDepartmentInfoByDEPTCD(params);
        } catch (Exception e) {
            log.error("Error while fetching Department info: ", e);
            return null;
        }
    }




    // 사업장 추가
    public int insertDepartment(DepartmentDTO departmentDTO) {
        try {
            int insertResult = departmentDao.insertDepartment(departmentDTO);
            //log.info("Insert Department Service", insertResult, departmentDTO);
            log.info("Insert Department Service", insertResult);
            return insertResult;
        } catch (Exception e) {
            log.error("Error while inserting Department: ", e);
            return 0;
        }
    }

    // 사업장 수정
    public int updateDepartment(DepartmentDTO departmentDTO) {
        try {
            int updateResult = departmentDao.updateDepartment(departmentDTO);
            //log.info("Update Department Service", updateResult, departmentDTO);
            log.info("Update Department Service", updateResult);
            return updateResult;
        } catch (Exception e) {
            log.error("Error while updating Department: ", e);
            return 0;
        }
    }
}
