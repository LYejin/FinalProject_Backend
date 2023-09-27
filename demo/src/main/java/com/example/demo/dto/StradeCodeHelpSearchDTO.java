package com.example.demo.dto;

import lombok.Data;

@Data
public class StradeCodeHelpSearchDTO {
    private String VALUE;     // 검색어 값
    private String USE_YN;    // 사용여부
    private String CO_CD;     // 회사 코드
    private String TR_FG;     // 거래처 구분
}
