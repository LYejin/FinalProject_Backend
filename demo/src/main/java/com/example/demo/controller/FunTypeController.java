package com.example.demo.controller;

import com.example.demo.dto.FunTypeDTO;
import com.example.demo.service.FunTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/accounting/user/fundType/")
@CrossOrigin("http://localhost:3000")
public class FunTypeController {

    @Autowired
    private FunTypeService funTypeService;

    @GetMapping("dupCheck/{checkData}")
    public  ResponseEntity<?> dupCheck(@PathVariable(value ="checkData") String CASH_CD){
        String dupValue = funTypeService.dupCheck(CASH_CD);

        return new ResponseEntity<String>(dupValue, HttpStatus.OK);
    }

    @GetMapping("searchRow")
    public ResponseEntity<?> searchRow(FunTypeDTO funTypeDTO){
        List<FunTypeDTO> funTypeDTOS = funTypeService.searchRow(funTypeDTO);

        return new ResponseEntity<List<FunTypeDTO>>(funTypeDTOS, HttpStatus.OK);
    }


    @PostMapping("fundTypeInsert")
        public ResponseEntity<?> fundTypeInsert(@RequestBody FunTypeDTO funTypeDTO){

        funTypeService.fundTypeInsert(funTypeDTO);

        return new ResponseEntity<String>("성공", HttpStatus.OK);
    }

    @PutMapping("fundTypeUpdate")
    public ResponseEntity<?> fundTypeUpdate(@RequestBody FunTypeDTO funTypeDTO){

        funTypeService.fundTypeUpdate(funTypeDTO);

        return new ResponseEntity<String>("성공", HttpStatus.OK);
    }

    @DeleteMapping("fundTypeDelete")
    public ResponseEntity<?> fundTypeDelete(@RequestBody List<String> checkList){
        log.info("체크된"+checkList);
        funTypeService.fundTypeDelete(checkList);

        return new ResponseEntity<String>("성공", HttpStatus.OK);
    }



}
