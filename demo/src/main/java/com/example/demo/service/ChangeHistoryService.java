package com.example.demo.service;

import com.example.demo.dao.ChangeHistoryDao;
import com.example.demo.dto.ChangeHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChangeHistoryService {

    @Autowired
    private ChangeHistoryDao changeHistoryDao;

    public Map<String, String> getEMP_CDAndDIV_NM(String USERNAME){
        Map<String, String> resultMap = new HashMap<>();

        try {
            resultMap = changeHistoryDao.getEMP_CDAndDIV_NM(USERNAME);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return resultMap;
    }
    public void changeHistoryInset(ChangeHistoryDTO  changeHistoryDTO){

        try {
            changeHistoryDao.changeHistoryInset(changeHistoryDTO);
            System.out.println("타냐?");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
