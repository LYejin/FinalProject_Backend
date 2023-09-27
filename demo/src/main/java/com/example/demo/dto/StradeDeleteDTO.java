package com.example.demo.dto;

import lombok.Data;

@Data
public class StradeDeleteDTO {
    private String TR_CD;
    private String CO_CD;
    private String TR_FG;

//    public  StradeDeleteDTO(StradeDeleteDTO param){
//        this.setTR_CD(param.getTR_CD() != null && !param.getTR_CD().equals("") ? param.getTR_CD() : "");
//        this.setCO_CD(param.getCO_CD() != null && !param.getCO_CD().equals("") ? param.getCO_CD() : "");
//        this.setTR_FG(param.getTR_FG() != null && !param.getTR_FG().equals("") ? param.getTR_FG() : "");
//    }
}
