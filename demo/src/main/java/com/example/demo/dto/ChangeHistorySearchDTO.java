package com.example.demo.dto;

import lombok.Data;

@Data
public class ChangeHistorySearchDTO {

    private String IDENTIFY_COLUMN_NAME;
    private String IDENTIFY_VALUE;
    private String TABLENAME;
    private String CHD_TARGET;
    private String CH_DT;
}
