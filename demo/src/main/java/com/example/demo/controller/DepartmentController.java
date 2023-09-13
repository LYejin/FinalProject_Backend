package com.example.demo.controller;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("system/user/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getDeptList/{CO_CD}")
    public List<DepartmentDTO> getDepartmentHierarchy(@PathVariable("CO_CD") String CO_CD) {
        // 서비스에서 계층 구조를 가져옵니다.
        log.info("Received request for CO_CD: {}", CO_CD);

        List<DepartmentDTO> departmentHierarchy = departmentService.getDepartmentHierarchy(CO_CD);
        return departmentHierarchy;
    }
}
