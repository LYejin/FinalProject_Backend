package com.example.demo.dto;

import lombok.Data;
import lombok.*;

@Data
public class DeptEmpListDTO {

    private String CO_CD; // 회사코드
    private String CO_NM; // 회사이름
    private String DEPT_CD; // 부서코드
    private String DEPT_NM; // 부서이름
    private String USER_YN; // 사용여부
    private String USERNAME; // 아이디
    private String KOR_NM; // 이름
    private String EMAIL_ADD; // 메일ID
    private String TEL; // 전화번호
    private String JOIN_DT; // 입사일
    private String PIC_FILE_ID; // 사진파일명
    private String ENRL_FG; // 재직구분
    private String PERSONAL_MAIL; // 개인메일ID

}
