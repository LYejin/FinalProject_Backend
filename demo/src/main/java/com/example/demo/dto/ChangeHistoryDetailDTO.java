package com.example.demo.dto;

import lombok.Data;

@Data
public class ChangeHistoryDetailDTO {
    private int CHD_CD;              //변경이력상세번호
    private int CH_CD;               //변경이력변호
    private String EMP_CD;           //사원번호
    private String CO_CD;            //회사번호
    private String CHD_ITEM;        //변경항목명
    private String CHD_DT;          //데이터타입
    private String CHD_BT;          //변경전데이터
    private String CHD_AT;          //변경후데이터
    private String CHD_PIC_AT;      //변경전사진데이터
    private String CHD_PIC_BT;      //변경후사진데이터
}
