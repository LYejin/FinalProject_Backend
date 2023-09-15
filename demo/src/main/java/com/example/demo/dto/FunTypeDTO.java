package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunTypeDTO {
    @JsonProperty("CASH_CD")
    private String CASH_CD;     //자금과목코드
    @JsonProperty("CO_CD")
    private String CO_CD;       //회사코드
    @JsonProperty("CASH_NM")
    private String CASH_NM;     //자금과목명
    @JsonProperty("CASH_FG")
    private String CASH_FG;     //수지 구분
    @JsonProperty("SUM_CD")
    private String SUM_CD;      //상워과목코드
    @JsonProperty("SUM_NM")
    private String SUM_NM;      //상위과목명
    @JsonProperty("RMK_CD")
    private String RMK_CD;      //비고
    @JsonProperty("INSERT_ID")
    private String INSERT_ID;   //추가자ID
    @JsonProperty("INSERT_DT")
    private String INSERT_DT;   //추가날짜
    @JsonProperty("MODIFY_ID")
    private String MODIFY_ID;   //갱신자ID
    @JsonProperty("MODIFY_DT")
    private String MODIFY_DT;   //갱신날짜
    @JsonProperty("TYPE_NM")
    private String TYPE_NM;     //용도
    @JsonProperty("LEVEL_CD")
    private String LEVEL_CD;    //레벨코드
    @JsonProperty("LOW_YN")
    private String LOW_YN;      //최하위여부
    @JsonProperty("USE_YN")
    private String USE_YN;      //사용여부
    @JsonProperty("DISP_SQ")
    private BigDecimal DISP_SQ; //정렬구분


    private List<String> columnsToUpdate;

}
