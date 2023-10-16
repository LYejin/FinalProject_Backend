package com.example.demo.dto;

import lombok.Data;

@Data
public class AcashFixDTO {
    private String CO_CD;       //회사코드
    private Integer SQ_NB;      //등록번호(시퀀스)
    private String DIV_CD;      //사업장코드
    private String DEAL_DD;     //지급일
    private String RMK_DC;      //적요
    private String CASH_AM;     //금액
    private String FR_DT;       //시작일
    private String TO_DT;       //종료일
    private String PJT_CD;      //프로젝트
    private String DEAL_PD;     //지급월
    private String DISP_SQ;     //수입,지출 구분
    private String CASH_CD;     //자금과목코드
    private String TR_CD;       //거래처코드
    private String FTR_CD;      //금융거래처 코드

    private String CASH_NM;   //자금등록 이름
    private String TR_NM;     //일반거래처 이름
    private String FTR_NM;    //금융거래처 이름
    private String BA_NB_TR;  //계좌번호
    private String BANK_NAME; //은행이름
//    private String EMP_CD;      //입력자?
//    private String DEAL_MM;
}
