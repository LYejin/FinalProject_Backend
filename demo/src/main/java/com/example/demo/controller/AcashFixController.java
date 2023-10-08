package com.example.demo.controller;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.dto.AcashFixDTO;
import com.example.demo.dto.WorkplaceDTO;
import com.example.demo.service.AcashFixService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
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
    @Autowired
    private HttpServletRequest request;

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
        Claims claims = getUserInfo();
        String CO_CD = String.valueOf(claims.get("CO_CD"));
        try {
            Map<String, String> map = new HashMap<>();
            map.put("CO_CD", checkNull(CO_CD));
            map.put("DIV_CD", checkNull(DIV_CD));
            map.put("DISP_SQ", checkNull(DISP_SQ));
            map.put("CASH_CD", checkNull(CASH_CD));
            map.put("TR_CD", checkNull(TR_CD));
            map.put("FTR_CD", checkNull(FTR_CD));
            map.put("FR_DT1", checkNullAndFormat(FR_DT1));
            map.put("FR_DT2", checkNullAndFormat(FR_DT2));
            map.put("TO_DT1", checkNullAndFormat(TO_DT1));
            map.put("TO_DT2", checkNullAndFormat(TO_DT2));

            System.out.println("DIV_CD : " + DIV_CD + "\n"+ " DISP_SQ : " + DISP_SQ + "\n" +" CASH_CD : " + CASH_CD + "\n"
                    + " TR_CD : " +TR_CD+ "\n" + " FTR_CD :" +FTR_CD + "\n" + " FR_DT1 : " +FR_DT1 + "\n"+ " FR_DT2 : " + FR_DT2 + "\n"
            +" TO_DT1 : " + TO_DT1 + "\n" +" TO_DT2 : " + TO_DT2);
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
            Claims claims = getUserInfo();
            String CO_CD = String.valueOf(claims.get("CO_CD"));
            acashFixDTO.setCO_CD(CO_CD);
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

            Claims claims = getUserInfo();
            String CO_CD = String.valueOf(claims.get("CO_CD"));
            acashFixDTO.setCO_CD(CO_CD);

            int result = acashFixService.AcashFixUpdate(acashFixDTO);
            //log.info("Update AcashFix Controller", acashFixDTO, result);
            log.info("Update AcashFix Controller");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while updating AcashFix: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //고정자금 삭제
    @DeleteMapping("delete")
    public ResponseEntity<Integer> deleteWorkplace(
            @RequestParam(name = "DIV_CD", required = true) String DIV_CD,
            @RequestParam(name = "SQ_NB", required = true) String[] SQ_NB
    ) {
        try {
            Claims claims = getUserInfo();
            String CO_CD = String.valueOf(claims.get("CO_CD"));
            Map<String, Object> map = new HashMap<>();
            map.put("CO_CD", CO_CD);
            map.put("DIV_CD", DIV_CD);
            map.put("SQ_NB", SQ_NB); // 이 부분이 배열이므로 Map 타입을 Object로 변경해야 함
            int result = acashFixService.deleteAcashFix(map);
            log.info("Delete AcashFix Controller");

            if (result == 0) {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while deleting AcashFix", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Claims getUserInfo() {
        String username = null;
        try {
            String header = request.getHeader(JwtProperties.HEADER_STRING);
            header = URLDecoder.decode(header, "UTF-8");

            String token = header.replace(JwtProperties.TOKEN_PREFIX, "");


            SecretKey key = Keys.hmacShaKeyFor(JwtProperties.getSecretKey());

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            username = String.valueOf(claims.get("username"));


            return claims;

        } catch (Exception e) {
            log.error("Token validation failed: " + e.getMessage());
        }
        return null;
    }

    //연간 고정자금 금액
    @GetMapping("/yearlyAmounts")
    public List<Map<String, Object>> getYearlyAmounts(@RequestParam int year,
                                                      @RequestParam String CO_CD,
                                                      @RequestParam String DIV_CD,
                                                      @RequestParam String DISP_SQ) {
        Map<String, Object> params = new HashMap<>();
        params.put("inputYear", year);
        params.put("CO_CD", CO_CD);
        params.put("DIV_CD", DIV_CD);
        params.put("DISP_SQ", DISP_SQ);
        return acashFixService.getYearlyAmounts(params);
    }

    @GetMapping("/quarterly")
    public ResponseEntity<List<Map<String, Object>>> getQuarterlyAmounts(@RequestParam int inputYear,
                                                                         @RequestParam String CO_CD,
                                                                         @RequestParam String DIV_CD,
                                                                         @RequestParam String DISP_SQ) {
        Map<String, Object> params = new HashMap<>();
        params.put("inputYear", inputYear);
        params.put("CO_CD", CO_CD);
        params.put("DIV_CD", DIV_CD);
        params.put("DISP_SQ", DISP_SQ);

        List<Map<String, Object>> amounts = acashFixService.getQuarterlyAmounts(params);
        if (amounts != null && !amounts.isEmpty()) {
            return new ResponseEntity<>(amounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyAmounts(@RequestParam int inputYear,
                                                                       @RequestParam String CO_CD,
                                                                       @RequestParam String DIV_CD,
                                                                       @RequestParam String DISP_SQ)  {
        Map<String, Object> params = new HashMap<>();
        params.put("inputYear", inputYear);
        params.put("CO_CD", CO_CD);
        params.put("DIV_CD", DIV_CD);
        params.put("DISP_SQ", DISP_SQ);

        List<Map<String, Object>> amounts = acashFixService.getMonthlyAmounts(params);
        if (amounts != null && !amounts.isEmpty()) {
            return new ResponseEntity<>(amounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dailyAmount")
    public ResponseEntity<List<Map<String, Object>>> getDailyAmounts(
            @RequestParam("DIV_CD") String divCd,
            @RequestParam("CO_CD") String coCd,
            @RequestParam("Year") String year,
            @RequestParam("Month") String month,
            @RequestParam("DISP_SQ") String dispSq) {

        Map<String, Object> params = new HashMap<>();
        params.put("DIV_CD", divCd);
        params.put("CO_CD", coCd);
        params.put("inputYear", year);
        params.put("inputMonth", month);
        params.put("DISP_SQ", dispSq);

        List<Map<String, Object>> dailyAmounts = acashFixService.getDailyAmounts(params);
        return new ResponseEntity<>(dailyAmounts, HttpStatus.OK);
    }



}
