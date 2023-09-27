package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class StradeRollManageDeleteDTO {
    private String TR_CD;
    private String CO_CD;
    private List<String> TRMG_SQ;

//    public StradeRollManageDeleteDTO(StradeRollManageDeleteDTO param){
//        this.setTR_CD(param.getTR_CD() != null && !param.getTR_CD().equals("") ? param.getTR_CD() : "");
//        this.setCO_CD(param.getCO_CD() != null && !param.getCO_CD().equals("") ? param.getCO_CD() : "");
//        this.setTRMG_SQ(param.getTRMG_SQ() != null && !param.getTRMG_SQ().isEmpty() ? param.getTRMG_SQ() : new ArrayList<>());
//    }
}
