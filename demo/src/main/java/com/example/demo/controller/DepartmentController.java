package com.example.demo.controller;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
