package com.example.demo.service;

import com.example.demo.dao.CommonDao;
import com.example.demo.dao.EmployeeDao;
import com.example.demo.dto.MainSidebarDTO;
import com.example.demo.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommonService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private EmployeeDao employeeDao;

    // 거래처코드도움 모달창 list
    public List<MainSidebarDTO> mainSidebarList(String username) {
        log.info("MainSidebarListService 실행");

        List<MainSidebarDTO> mainSidebarList = new ArrayList<>();

        UserDTO userInfo = null;

        try {
            userInfo = employeeDao.userSelect(username);
            System.out.println(userInfo.getROLE_NAME());
            mainSidebarList = commonDao.mainSidebarList(userInfo.getROLE_NAME());
            System.out.println("------" + mainSidebarList);
        } catch (Exception e) {
            log.error("MainSidebarListService Error : mainSidebarList={}, errorMessage={}",mainSidebarList,e.getMessage());
        }
        return mainSidebarList;
    }
}
