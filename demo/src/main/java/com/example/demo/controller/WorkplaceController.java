package com.example.demo.controller;

import com.example.demo.dto.WorkplaceDTO;
import com.example.demo.service.WorkplaceService;
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
@RequestMapping("system/user/WorkplaceManage/")
@CrossOrigin("http://localhost:3000")
public class WorkplaceController {

    private final WorkplaceService workplaceService;

    @Autowired
    public WorkplaceController(WorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    //사업장 목록
    @GetMapping("getList")
    public ResponseEntity<List<WorkplaceDTO>> getAllWorkplaces(
            @RequestParam(name = "DIV_CD", required = false) String DIV_CD,
            @RequestParam(name = "DIV_NM", required = false) String DIV_NM,
            @RequestParam(name = "CO_CD", required = false) String CO_CD,
            @RequestParam(name = "DIV_YN", required = false) String DIV_YN
    ) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("DIV_CD", DIV_CD);
            map.put("DIV_NM", DIV_NM);
            map.put("CO_CD", CO_CD);
            map.put("DIV_YN", DIV_YN);
            List<WorkplaceDTO> list = workplaceService.selectWorkplaceSearch(map);
            log.info("Get Workplace List Controller");
            //log.info("Get Workplace List",list);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching all workplaces: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //사업장 상세
//    @GetMapping("getWorkpInfo/{divCd}")
//    public ResponseEntity<WorkplaceDTO> getWorkplaceInfo(@PathVariable String divCd) {
//        try {
//            WorkplaceDTO wpInfo = workplaceService.selectWorkplaceInfoByDIVCD(divCd);
//            //log.info("Get Workplace Detail Controller", divCd, wpInfo);
//            log.info("Get Workplace Detail Controller" + divCd);
//            return new ResponseEntity<>(wpInfo, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Error while fetching workplace info for DIV_CD {}: ", divCd, e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    //사업장 상세
    @GetMapping("getWorkpInfo")
    public ResponseEntity<WorkplaceDTO> getWorkplaceInfo(
            @RequestParam String divCd,
            @RequestParam String coCd) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("DIV_CD", divCd);
            params.put("CO_CD", coCd);
            WorkplaceDTO wpInfo = workplaceService.selectWorkplaceInfoByDIVCD(params);
            log.info("Get Workplace Detail Controller divCd: " + divCd + ", coCd: " + coCd);
            return new ResponseEntity<>(wpInfo, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching workplace info for DIV_CD {}: ", divCd, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 사업장 추가
    @PostMapping("insert")
    public ResponseEntity<Integer> InsertWorkplace(@RequestBody WorkplaceDTO workplaceDTO) {
        try {
            int result = workplaceService.workplaceInsert(workplaceDTO);
            //log.info("Inserted Workplace Controller", workplaceDTO, result);
            log.info("Insert Workplace Controller");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while inserting workplace: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //사업장 수정
    @PutMapping("update")
    public ResponseEntity<Integer> updateWorkplace(@RequestBody WorkplaceDTO workplaceDTO) {
        try {
            int result = workplaceService.workplaceUpdate(workplaceDTO);
            //log.info("Update Workplace Controller", workplaceDTO, result);
            log.info("Update Workplace Controller");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while updating workplace: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //사업장 삭제
    @PutMapping("delete/{DIV_CD}/{CO_CD}")
    public ResponseEntity<Integer> deleteWorkplace(@PathVariable String DIV_CD, @PathVariable String CO_CD) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("DIV_CD", DIV_CD);
            params.put("CO_CD", CO_CD);
            int result = workplaceService.workplaceRemove(params);
            log.info("Delete Workplace Controller, DIV_CD: {}, CO_CD: {}, Result: {}", DIV_CD, CO_CD, result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while deleting workplace with DIV_CD: {} and CO_CD: {}", DIV_CD, CO_CD, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}