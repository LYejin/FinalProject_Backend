package com.example.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class UserDTO {

    private String ROLE_NAME;//사원권한
    private String USERNAME; //계정 ID
    private String PASSWORD; //계정 비번
    private String EMP_CD; //사원코드
    private String DIV_CD; //
    private String CO_CD;


    public List<String> getRoleList() {
        if (!this.ROLE_NAME.isEmpty()) {
            return Arrays.asList(this.ROLE_NAME.split(","));
        }
        return new ArrayList<>();
    }
}

