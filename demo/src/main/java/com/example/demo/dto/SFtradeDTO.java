package com.example.demo.dto;

import lombok.Data;

@Data
public class SFtradeDTO {
    private String TR_CD;
    private String CO_CD;
    private String TR_NM;
    private String TR_FG;
    private String USE_YN;
    private String VIEW_YN;
    private String FSTART_DT;
    private String FEND_DT;
    private String BA_NB_TR;
    private String DEPOSITOR;
    private String DEPOSIT_NM;
    private String ACCOUNT_OPEN_BN;
    private String BANK_CD;
    private String BANK_NAME;

//    public  SFtradeDTO(SFtradeDTO param){
//        this.setTR_CD(param.getTR_CD() != null && !param.getTR_CD().equals("") ? param.getTR_CD() : "");
//    }
}
