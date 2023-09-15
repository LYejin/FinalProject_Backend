package com.example.demo.dto;

import lombok.Data;

@Data
public class EmpCodeHelpDTO {
    private String EMP_CD; // 사원계정
    private String KOR_NM; // 이름
    private String DEPT_NM; // 부서명
    private String DIV_NM; // 사업장명
}
