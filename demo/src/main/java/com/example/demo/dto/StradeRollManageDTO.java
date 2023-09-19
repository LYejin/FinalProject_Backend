package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class StradeRollManageDTO {
    private int trmgSq;
    private int TRMG_SQ;
    private String TR_CD;
    private String CO_CD;
    private String DEPT_CD;
    private String DEPT_NM;
    private String EMP_CD;
    private String KOR_NM;
    private String MANAGE_JOB;
    private String PHONE_NB;
    private String EXT_NB;
    private String MOBILE_NB;
    private String MAIL_ADDR;
    private String MANAGE_CG;
    private String JOB_GRADE;
    private String NOTE;
    private String USE_YN;
    private Timestamp INSERT_DT;
    private String ROLL_FG;
}

