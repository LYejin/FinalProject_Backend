package com.example.demo.controller;

import com.example.demo.dto.ChangeHistoryDTO;
import com.example.demo.dto.ChangeHistorySearchDTO;
import com.example.demo.dto.CompanyDTO;
import com.example.demo.service.ChangeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("system/admin/groupManage/")
@CrossOrigin("http://localhost:3000")
public class ChangeHistoryComtroller {

    @Autowired
    private ChangeHistoryService changeHistoryService;

    @GetMapping("ChangeHistorySelect/{CH_CATEGORY}")
    public ResponseEntity<?> getChangeHistoryList(@PathVariable(value = "CH_CATEGORY") String CH_CATEGORY){
        List<ChangeHistoryDTO> changeHistorySearchDTOS = null;


        changeHistorySearchDTOS = changeHistoryService.getChangeHistoryList(CH_CATEGORY);

        return new ResponseEntity<List<ChangeHistoryDTO>>(changeHistorySearchDTOS, HttpStatus.OK);
    }

}
