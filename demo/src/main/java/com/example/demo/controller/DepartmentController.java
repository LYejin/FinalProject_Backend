package com.example.demo.controller;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.WorkplaceDTO;
import com.example.demo.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("system/user/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getDeptList/{CO_CD}")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentHierarchy(@PathVariable("CO_CD") String CO_CD) {
        try {
            List<DepartmentDTO> departmentHierarchy = departmentService.getDepartmentHierarchy(CO_CD);
            log.info("Get Department List Controller", CO_CD);
            return new ResponseEntity<>(departmentHierarchy, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching departments for CO_CD: " + CO_CD, e);
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //사업장 상세
    @GetMapping("getDeptInfo/{deptCd}")
    public ResponseEntity<DepartmentDTO> getDepartmentInfo(@PathVariable String deptCd) {
        try {
            DepartmentDTO deptInfo = departmentService.selectDepartmentInfoByDEPTCD(deptCd);
            log.info("Get Department Detail Controller deptCd: {} deptInfo: {}", deptCd, deptInfo);
            log.info("Get Department Detail Controller" + deptCd);
            return new ResponseEntity<>(deptInfo, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching Department info for DIV_CD {}: ", deptCd, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //부서 추가
    @PostMapping("insert")
    public ResponseEntity<Integer> insertDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            int result = departmentService.insertDepartment(departmentDTO);
            //log.info("Inserted Department Controller", departmentDTO, result);
            log.info("Insert Department Controller");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while inserting Department: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //부서 수정
    @PutMapping("update")
    public ResponseEntity<Integer> updateDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            int result = departmentService.updateDepartment(departmentDTO);
            //log.info("Update Department Controller", departmentDTO, result);
            log.info("Update Department Controller");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while updating Department: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
