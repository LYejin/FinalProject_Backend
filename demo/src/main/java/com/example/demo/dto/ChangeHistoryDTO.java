package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChangeHistoryDTO {
    private int CH_CD;          //변경이력번호    자동생성
    private String EMP_CD;      //사원번호
    private String CO_CD;       //회사번호
    private String CH_CATEGORY; //변경카테고리    메소드명 앞단으로 확인
    private String CH_DT;       //변경일시       DATE 객체
    private String CH_DIVISION; //변경구분       메소드명 뒷단으로 확인
    private String CH_IM;       //변경정보       메소드명 뒷단으로 확인(~~정보가 수정되었습니다)
    private String CH_NM;       //변경자명       사원 조인
    private String CH_IP;       //변경자IP       IP객체 사용하기
    private String CHD_TARGET_NM;  //변경된 대상
    private String CHD_TARGET_CO_NM;       //회사명         매개변수 CompanyDTO객체

    private String startDate;
    private String endDate;

    private List<String> columnsToUpdate;
}
