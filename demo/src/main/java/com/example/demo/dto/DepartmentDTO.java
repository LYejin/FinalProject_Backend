package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentDTO {
    private String DEPT_CD;     //부서코드
    private String CO_CD;       //회사코드
    private String DIV_CD;      //사업장코드
    private String CALL_YN;     //대내수신여부
    private String Field2;      //행정코드
    private String CALL_NM;     //발신인명
    private String DEPT_CT;     //부서유형
    private String DEPT_NM;     //부서명
    private String DEPT_NMK;    //부서약칭
    private String APPR_NM;     //승인자명
    private String MGR_NM;      //관리자명
    private String DEPT_YN;     //사용여부
    private String MGR_YN;      //관리여부
    private String SHOW_YN;     //조직도표시여부
    private String SORT_YN;     //정렬
    private String MDEPT_CD;    //상위부서명
    private String CO_NM;       //회사이름
    private String DIV_NM;      //사업장이름
}
