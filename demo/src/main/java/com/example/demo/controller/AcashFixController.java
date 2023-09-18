package com.example.demo.controller;

import com.example.demo.dto.AcashFixDTO;
import com.example.demo.dto.WorkplaceDTO;
import com.example.demo.service.AcashFixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("system/user/AcashFixManage/")
@CrossOrigin("http://localhost:3000")
public class AcashFixController {

    private final AcashFixService acashFixService;

    @Autowired
    public AcashFixController(AcashFixService acashFixService) {
        this.acashFixService = acashFixService;
    }

    @GetMapping("getList")
    public ResponseEntity<List<AcashFixDTO>> getAllAcashFix(
            @RequestParam(name = "DIV_CD", required = true) String DIV_CD,
            @RequestParam(name = "DISP_SQ", required = false) String DISP_SQ,
            @RequestParam(name = "TR_CD", required = false) String TR_CD,
            @RequestParam(name = "FTR_CD", required = false) String FTR_CD,
            @RequestParam(name = "FR_DT1", required = false) String FR_DT1,
            @RequestParam(name = "FR_DT2", required = false) String FR_DT2
    ) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("DIV_CD", DIV_CD);
            map.put("DISP_SQ", DISP_SQ);
            map.put("TR_CD", TR_CD);
            map.put("FTR_CD", FTR_CD);
            map.put("FR_DT1", FR_DT1);
            map.put("FR_DT2", FR_DT2);

            List<AcashFixDTO> list = acashFixService.selectAcashFixSearch(map);
            log.info("Get AcashFix List Controller"+list);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching all AcashFix: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 고정자금 추가
    @PostMapping("insert")
    public ResponseEntity<Integer> AcashFixInsert(@RequestBody AcashFixDTO acashFixDTO) {
        try {
            int result = acashFixService.AcashFixInsert(acashFixDTO);
            //log.info("Inserted AcashFix Controller", acashFixDTO, result);
            log.info("Insert AcashFix Controller");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while inserting AcashFix: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //고정자금 수정
    @PutMapping("update")
    public ResponseEntity<Integer> AcashFixUpdate(@RequestBody AcashFixDTO acashFixDTO) {
        try {
            int result = acashFixService.AcashFixUpdate(acashFixDTO);
            //log.info("Update AcashFix Controller", acashFixDTO, result);
            log.info("Update AcashFix Controller");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while updating AcashFix: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
