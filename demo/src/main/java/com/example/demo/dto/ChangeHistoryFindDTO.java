package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeHistoryFindDTO{
        @JsonProperty("CH_CD")         // 변경이력번호    자동생성
        private int CH_CD;

        @JsonProperty("EMP_CD")        // 사원번호
        private String EMP_CD;

        @JsonProperty("CO_CD")         // 회사번호
        private String CO_CD;

        @JsonProperty("CH_CATEGORY")   // 변경카테고리    메소드명 앞단으로 확인
        private String CH_CATEGORY;

        @JsonProperty("CH_DT")         // 변경일시       DATE 객체
        private String CH_DT;

        @JsonProperty("CH_DIVISION")   // 변경구분       메소드명 뒷단으로 확인
        private String CH_DIVISION;

        @JsonProperty("CH_IM")         // 변경정보       메소드명 뒷단으로 확인(~~정보가 수정되었습니다)
        private String CH_IM;

        @JsonProperty("CH_NM")         // 변경자명       사원 조인
        private String CH_NM;

        @JsonProperty("CH_IP")         // 변경자IP       IP객체 사용하기
        private String CH_IP;

        @JsonProperty("CHD_TARGET_NM") // 변경된 대상
        private String CHD_TARGET_NM;

        @JsonProperty("CHD_TARGET_CO_NM") // 회사명         매개변수 CompanyDTO객체
        private String CHD_TARGET_CO_NM;

        @JsonProperty("startDate")
        private String startDate; // 시작날짜
        @JsonProperty("endDate")
        private String endDate;   // 종료날짜

        private List<String> columnsToUpdate; // 업데이트할 컬럼 리스트{
}
