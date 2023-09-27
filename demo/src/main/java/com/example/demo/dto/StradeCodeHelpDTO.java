package com.example.demo.dto;

import lombok.Data;

@Data
public class StradeCodeHelpDTO {
    private String TR_CD; // 거래처 코드
    private String TR_NM; // 거래처명
    private String TR_FG; // 구분
    private String REG_NB; // 사업자번호
    private String CEO_NM; // 대표자명
    private String BA_NB_TR; // 계좌번호
}
