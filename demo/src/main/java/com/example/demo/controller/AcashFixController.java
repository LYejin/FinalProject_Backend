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
@RequestMapping("accounting/user/AcashFixManage/")
@CrossOrigin("http://localhost:3000")
public class AcashFixController {

    private final AcashFixService acashFixService;

    @Autowired
    public AcashFixController(AcashFixService acashFixService) {
        this.acashFixService = acashFixService;
    }

    private String checkNullAndFormat(String date) {
        if (date == null || date.isEmpty() || "null".equals(date)) {
            return null;
        }
        return date.replace("-", "");
    }

    @GetMapping("getList")
    public ResponseEntity<List<AcashFixDTO>> getAllAcashFix(
            @RequestParam(name = "DIV_CD", required = true) String DIV_CD,
            @RequestParam(name = "DISP_SQ", required = false) String DISP_SQ,
            @RequestParam(name = "CASH_CD", required = false) String CASH_CD,
            @RequestParam(name = "TR_CD", required = false) String TR_CD,
            @RequestParam(name = "FTR_CD", required = false) String FTR_CD,
            @RequestParam(name = "FR_DT1", required = false) String FR_DT1,
            @RequestParam(name = "FR_DT2", required = false) String FR_DT2,
            @RequestParam(name = "TO_DT1", required = false) String TO_DT1,
            @RequestParam(name = "TO_DT2", required = false) String TO_DT2
    ) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("DIV_CD", checkNull(DIV_CD));
            map.put("DISP_SQ", checkNull(DISP_SQ));
            map.put("CASH_CD", checkNull(CASH_CD));
            map.put("TR_CD", checkNull(TR_CD));
            map.put("FTR_CD", checkNull(FTR_CD));
            map.put("FR_DT1", checkNullAndFormat(FR_DT1));
            map.put("FR_DT2", checkNullAndFormat(FR_DT2));
            map.put("TO_DT1", checkNullAndFormat(TO_DT1));
            map.put("TO_DT2", checkNullAndFormat(TO_DT2));

            System.out.println("DIV_SQ : " + DIV_CD + "\n"+ " DISP_SQ : " + DISP_SQ + "\n" +" CASH_CD : " + CASH_CD + "\n"
                    + " TR_CD : " +TR_CD+ "\n" + " FTR_CD :" +FTR_CD + "\n" + " FR_DT1 : " +FR_DT1 + "\n"+ " FR_DT2 : " + FR_DT2 + "\n"
            +" TO_DT1 : " + TO_DT1 + "\n" +" TO_DT2 : " + TO_DT2) ;
            List<AcashFixDTO> list = acashFixService.selectAcashFixSearch(map);
            log.info("Get AcashFix List Controller" +list);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching all AcashFix: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    private String checkNull(String value) {
        return "null".equals(value) ? null : value;
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
