package com.example.demo.dto;

import lombok.Data;

@Data
public class WorkplaceDTO {
    private String DIV_CD;      //사업장코드
    private String CO_CD;       //회사코드
    private String CO_NM;       //회사이름
    private String DIV_YN;      //사용여부
    private String FILL_YN;     //본점여부
    private String DIV_NM;      //사업장이름
    private String DIV_ADDR;    //사업장주소
    private String DIV_TEL;     //사업장 전화번호
    private String REG_NB;      //사업자번호
    private String DIV_NMK;     //사업장약칭
    private String BUSINESS;    //업태
    private String JONGMOK;     //종목
    private String OPEN_DT;     //개업일
    private String CLOSE_DT;    //폐업일
    private String MAS_NM;      //관리자명
    private String DIV_FAX;     //FAX번호
    private String COP_NB;      //법인번호
    private String ADDR_CD;     //우편번호
    private String ADDR_NUM;    //상세주소g
    private String PIC_FILE_ID; //부서이미지

}
