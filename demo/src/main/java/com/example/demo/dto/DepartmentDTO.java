package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentDTO {
    private String DEPT_CD;     //부서코드
    private String CO_CD;       //회사코드
    private String DIV_CD;      //사업장코드
    private String CALL_YN;     //대내수신여부
    private String CALL_NM;     //발신인명
    private String DEPT_CT;     //부서유형
    private String DEPT_NM;     //부서명
    private String DEPT_NMK;    //부서약칭
    private String MGR_NM;      //관리자명
    private String DEPT_YN;     //사용여부
    private String SHOW_YN;     //조직도표시여부
    private String SORT_YN;     //정렬
    private String MDEPT_CD;    //상위부서명
    private String CO_NM;       //회사이름
    private String DIV_NM;      //사업장이름
    private String ADDR_CD;     //우편번호
    private String ADDR;        //주소
    private String ADDR_NUM;    //상세주소
    private String EMP_NB;      //직원수
}
