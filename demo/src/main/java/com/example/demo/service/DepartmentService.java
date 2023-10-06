package com.example.demo.service;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.dto.DeptEmpListDTO;
import com.example.demo.dto.WorkplaceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
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


    // 부서 추가
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

    // 부서 수정
    public int updateDepartment(DepartmentDTO departmentDTO) {
        try {
            int updateResult = departmentDao.updateDepartment(departmentDTO);
            log.info("Update Department Service", updateResult);

            // DEPT_YN 값이 0인 경우 추가 로직 수행
            if ("0".equals(departmentDTO.getDEPT_YN())) {
                Map<String, Object> params = new HashMap<>();
                params.put("CO_CD", departmentDTO.getCO_CD());
                params.put("DEPT_CD", departmentDTO.getDEPT_CD());

                int additionalResult = updateDepartmentAndEmployee(params);

                updateResult += additionalResult;
            }

            return updateResult;
        } catch (Exception e) {
            log.error("Error while updating Department: ", e);
            return 0;
        }
    }


    //부서코드 중복검사
    public boolean isDepartmentDuplicate(Map<String, String> params) {
        try {
            int count = departmentDao.checkDepartmentDuplicate(params);
            return count > 0;
        } catch (Exception e) {
            log.error("Error while check DepartmentCD: ", e);
            return false;
        }
    }

    public List<DeptEmpListDTO> getDeptEmpList(Map<String, String> params) {
        return departmentDao.selectDeptEmpList(params);
    }


    public boolean checkExistence(String CO_CD, String DEPT_CD) {
        Map<String, Object> params = new HashMap<>();
        params.put("CO_CD", CO_CD);
        params.put("DEPT_CD", DEPT_CD);

        int employeeCount = departmentDao.countEmployeeWithCondition(params);
        int departmentCount = departmentDao.countDepartmentWithCondition(params);

        return (employeeCount == 0) && (departmentCount == 0);
    }

    //부서삭제(하위부서 및 사원 사용여부 변경)
    @Transactional
    public int updateDepartmentAndEmployee(Map<String, Object> params) {
        try {
            int employeeUpdateCount1 = departmentDao.updateEmployeeUserYNWithDeptCD(params);
            int employeeUpdateCount2 = departmentDao.updateEmployeeUserYNWithMDeptCD(params);
            int departmentUpdateCount = departmentDao.updateDepartmentDeptYN(params);

            return employeeUpdateCount1 + employeeUpdateCount2 + departmentUpdateCount;
        } catch (Exception e) {
            // 음수 값 또는 다른 방식으로 오류 상황을 나타내도록 변경할 수 있습니다.
            return -1;
        }
    }

    //부서삭제
    public int deleteDepartment(DepartmentRequestDTO departmentRequestDTO) {
        try {
            return departmentDao.deleteDepartment(departmentRequestDTO);
        } catch (Exception e) {
            // 음수 값 또는 다른 방식으로 오류 상황을 나타내도록 변경할 수 있습니다.
            return -1;
        }
    }

}
