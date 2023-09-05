package com.example.demo.controller;

import com.example.demo.dto.WorkplaceDTO;
import com.example.demo.service.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("system/user/WorkplaceManage/")
@CrossOrigin("http://localhost:3000")
public class WorkplaceController {

    private final WorkplaceService workplaceService;

    @Autowired
    public WorkplaceController(WorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    @GetMapping("getList")
    public ResponseEntity<List<WorkplaceDTO>> getAllWorkplaces(
            @RequestParam(name = "DIV_CD", required = false) String DIV_CD,
            @RequestParam(name = "DIV_NM", required = false) String DIV_NM,
            @RequestParam(name = "CO_CD", required = false) String CO_CD,
            @RequestParam(name = "DIV_YN", required = false) String DIV_YN
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("DIV_CD", DIV_CD);
        map.put("DIV_NM", DIV_NM);
        map.put("CO_CD", CO_CD);
        map.put("DIV_YN", DIV_YN);
        List<WorkplaceDTO> list = workplaceService.selectWorkplaceSearch(map);
        return new ResponseEntity<List<WorkplaceDTO>>(list, HttpStatus.OK);
    }

    @GetMapping("getWorkpInfo/{divCd}")
    public ResponseEntity<WorkplaceDTO> getWorkplaceInfo(@PathVariable String divCd) {
        WorkplaceDTO wpInfo = null;
        wpInfo = workplaceService.selectWorkplaceInfoByDIVCD(divCd);
        return new ResponseEntity<WorkplaceDTO>(wpInfo, HttpStatus.OK);
    }

    @PostMapping("insert")
    public ResponseEntity<Integer> InsertWorkplace(@RequestBody WorkplaceDTO workplaceDTO) {
        int result = workplaceService.workplaceInsert(workplaceDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<Integer> updateWorkplace(@RequestBody WorkplaceDTO workplaceDTO) {
        int result = workplaceService.workplaceUpdate(workplaceDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("delete/{DIV_CD}")
    public ResponseEntity<Integer> deleteWorkplace(@PathVariable(value = "DIV_CD") String DIV_CD) {
        int result = workplaceService.workplaceRemove(DIV_CD);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}