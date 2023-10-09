package com.example.demo.controller;

import com.example.demo.dto.MainSidebarDTO;
import com.example.demo.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/common/page")
@CrossOrigin("http://localhost:3000")
public class CommonController {
    @Autowired
    private CommonService commonService;

    @GetMapping("mainSidebarList")
    public ResponseEntity<List<MainSidebarDTO>> mainSidebarList(@RequestParam(value = "USERNAME") String username) {
        log.info("mainSidebarListController 실행");

        List<MainSidebarDTO> mainSidebarList = null;
        try {
            mainSidebarList = commonService.mainSidebarList(username);
        } catch (Exception e) {
            log.error("mainSidebarListController Error : mainSidebarList={}, errorMessage={}", mainSidebarList, e.getMessage());
        }

        return new ResponseEntity<>(mainSidebarList, HttpStatus.OK);
    }
}
