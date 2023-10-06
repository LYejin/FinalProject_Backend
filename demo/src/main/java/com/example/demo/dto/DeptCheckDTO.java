package com.example.demo.dto;
import lombok.*;

@Data
public class DeptCheckDTO {

    private String CO_CD;    // 회사코드
    private String DEPT_CD;  // 부서코드
    private String USER_YN;  // 사용자사용여부
    private String MDEPT_CD; // 상위부서코드
    private String DEPT_YN;  // 부서사용여부
    //    private String USE_FG; // 조회권한
    //    private String ROLE_NAME = "ROLE_USER";
}
