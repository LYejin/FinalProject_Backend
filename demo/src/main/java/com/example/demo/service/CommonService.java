package com.example.demo.service;

import com.example.demo.dao.CommonDao;
import com.example.demo.dto.MainSidebarDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommonService {

    @Autowired
    private CommonDao commonDao;

    // 거래처코드도움 모달창 list
    public List<MainSidebarDTO> mainSidebarList() {
        log.info("MainSidebarListService 실행");
        List<MainSidebarDTO> mainSidebarList = new ArrayList<>();

        try {
            mainSidebarList = commonDao.mainSidebarList();
        } catch (Exception e) {
            log.error("MainSidebarListService Error : mainSidebarList={}, errorMessage={}",mainSidebarList,e.getMessage());
        }
        return mainSidebarList;
    }

}
