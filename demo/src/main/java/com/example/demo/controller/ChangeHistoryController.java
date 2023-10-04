package com.example.demo.controller;

import com.example.demo.dto.ChangeHistoryDTO;
import com.example.demo.dto.ChangeHistoryDetailDTO;
import com.example.demo.service.ChangeHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("system/admin/groupManage/")
@CrossOrigin("http://localhost:3000")
public class ChangeHistoryController {

    @Autowired
    private ChangeHistoryService changeHistoryService;

    @GetMapping("ChangeHistorySelect/{CH_CATEGORY}")
    public ResponseEntity<?> getChangeHistoryList(@PathVariable(value = "CH_CATEGORY") String CH_CATEGORY){
        List<ChangeHistoryDTO> changeHistorySearchDTOS = null;

        changeHistorySearchDTOS = changeHistoryService.getChangeHistoryList(CH_CATEGORY);

        return new ResponseEntity<List<ChangeHistoryDTO>>(changeHistorySearchDTOS, HttpStatus.OK);
    }

    @PostMapping("ChangeHistoryDetailList")
    public ResponseEntity<?> ChangeHistoryDetailList(@RequestBody ChangeHistoryDTO changeHistoryDTO){
        List<ChangeHistoryDetailDTO> changeHistoryDetailDTOS= new ArrayList<>();
        log.info("서치데이터:"+changeHistoryDTO);
        changeHistoryDetailDTOS = changeHistoryService.ChangeHistoryDetailList(changeHistoryDTO);

        return new ResponseEntity<List<ChangeHistoryDetailDTO>>(changeHistoryDetailDTOS, HttpStatus.OK);
    }

    @PostMapping("ChangeHistorySearch")
    public ResponseEntity<?> ChangeHistorySearch(@RequestBody ChangeHistoryDTO changeHistoryDTO ){
        List<ChangeHistoryDTO> changeHistorySearchDTOS = null;

        return new ResponseEntity<List<ChangeHistoryDTO>>(changeHistorySearchDTOS, HttpStatus.OK);
    }

}
