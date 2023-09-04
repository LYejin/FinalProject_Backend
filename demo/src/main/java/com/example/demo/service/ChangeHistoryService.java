package com.example.demo.service;

import com.example.demo.dao.ChangeHistoryDao;
import com.example.demo.dto.ChangeHistoryDTO;
import com.example.demo.dto.ChangeHistorySearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChangeHistoryService {

    @Autowired
    private ChangeHistoryDao changeHistoryDao;



    public String CH_NM_Select(String USERNAME){
        String CH_NM =null;
        try {
            System.out.println(USERNAME);
            CH_NM =changeHistoryDao.CH_NM_Select(USERNAME);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return CH_NM;
    }

    public Map<String, String> getCHD_TARGET(ChangeHistorySearchDTO changeHistorySearchDTO){
        Map<String, String> resultMap = null;
        try {

            resultMap  =changeHistoryDao.CHD_TARGET_select(changeHistorySearchDTO);
            System.out.println("서비스"+resultMap);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return resultMap;
    }

    public void changeHistoryInsert(ChangeHistoryDTO  changeHistoryDTO){

        try {
            changeHistoryDao.changeHistoryInset(changeHistoryDTO);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public List<ChangeHistoryDTO> getChangeHistoryList(String CH_CATEGORY) {
        List<ChangeHistoryDTO> changeHistorySearchDTOS = null;
        try {
            changeHistorySearchDTOS = changeHistoryDao.ChangeHistorySelect(CH_CATEGORY);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return changeHistorySearchDTOS;
    }


}
