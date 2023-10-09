package com.example.demo.dto;


import lombok.*;

@Data
public class EmployeeDTO {

    private String DIV_NM; // 사업장명
    private String DEPT_NM; // 부서명
    private String EMP_CD; // 사원계정
    private String CO_CD; // 회사코드
    private String DIV_CD; // 사업장코드
    private String DEPT_CD; // 부서코드
    private String USER_YN; // 사용여부
    private String USE_FG; // 조회권한
    private String USERNAME; // 아이디
    private String PASSWORD; // 비밀번호
    private String KOR_NM; // 이름
    private String EMAIL_ADD; // 메일ID
    private String TEL; // 전화번호
    private String GENDER_FG; // 성별
    private String JOIN_DT; // 입사일
    private String RTR_DT; // 퇴사일
    private String RSRG_ADD; // 주소지
    private String PIC_FILE_ID; // 사진파일명
    private String ENRL_FG; // 재직구분
    private String PERSONAL_MAIL; // 개인메일ID
    private String PERSONAL_MAIL_CP; // 개인메일회사
    private String SALARY_MAIL; // 급여메일ID
    private String SALARY_MAIL_CP; // 급여메일소속
    private String HOME_TEL; // 전화번호(집)
    private String ZIPCODE; // 우편번호
    private String ADDR; // 주소
    private String ADDR_NUM; // 번지
    private String ROLE_NAME = "ROLE_USER";
}
