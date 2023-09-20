package com.example.demo.dto;

import lombok.Data;

@Data
public class AcashFixDTO {
    private Integer SQ_NB;
    private String DIV_CD;
    private String DEAL_DD;
    private String RMK_DC;
    private String CASH_AM;
    private String FR_DT;
    private String TO_DT;
    private String PJT_CD;
    private String DEAL_PD;
    private String DEAL_MM;
    private String DISP_SQ;
    private String CASH_CD;
    private String TR_CD;
    private String EMP_CD;
    private String FTR_CD;

    private String CASH_NM;   //자금등록 이름
    private String TR_NM;     //일반거래처 이름
    private String FTR_NM;    //금융거래처 이름
    private String BA_NB_TR;  //계좌번호
    private String BANK_NAME; //은행이름
}
