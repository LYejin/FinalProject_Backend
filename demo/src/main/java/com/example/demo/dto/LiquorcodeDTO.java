package com.example.demo.dto;

import lombok.Data;

@Data
public class LiquorcodeDTO {
    private String LIQ_CD; // 주류코드
    private String WHOLESALE;  // 소매
    private String RETAIL; // 도매
}
