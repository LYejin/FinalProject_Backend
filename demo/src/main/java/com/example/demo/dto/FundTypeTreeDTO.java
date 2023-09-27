package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundTypeTreeDTO {
    private String CASH_FG;
    private String CASH_CD_L1;
    private String CASH_NM_L1;
    private String CASH_CD_L2;
    private String CASH_NM_L2;
    private String CASH_CD_L3;
    private String CASH_NM_L3;
    private String LOW_YN;
    private BigDecimal DISP_SQ;
    private String USE_YN;

    private String Level;
    private String Children;
}
