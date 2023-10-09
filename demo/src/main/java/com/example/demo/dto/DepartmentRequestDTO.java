package com.example.demo.dto;

import lombok.Data;

@Data
public class DepartmentRequestDTO { //부서삭제
    private String CO_CD;       //회사코드
    private String DEPT_CD;     //부서코드
}
