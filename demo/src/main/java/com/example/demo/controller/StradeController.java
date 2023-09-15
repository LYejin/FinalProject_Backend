package com.example.demo.controller;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.dto.*;
import com.example.demo.service.StradeService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/accounting/user/Strade")
@CrossOrigin("http://localhost:3000")
public class StradeController {

    @Autowired
    private StradeService stradeService;

    @Autowired
    private HttpServletRequest request;

    // 전체 일반 거래처 리스트 출력 및 검색 결과 출력
    @GetMapping("getSGtradeList")
    public ResponseEntity<List<SGtradeDTO>> sgtradeSearchList(
            @RequestParam(name = "TR_CD", required = false) String TR_CD,
            @RequestParam(name = "TR_NM", required = false) String TR_NM,
            @RequestParam(name = "REG_NB", required = false) String REG_NB,
            @RequestParam(name = "PPL_NB", required = false) String PPL_NB,
            @RequestParam(name = "USE_YN", required = false) String USE_YN) {
        log.info("일반 거래처 리스트 출력 Controller");

        // 사원이 속한 회사 코드, 사원 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));
        String EMP_CD = String.valueOf(claims.get("EMP_CD"));

        System.out.println("USE_YN : " + USE_YN);

        // 검색 조건들
        Map<String, String> map = new HashMap<>();
        map.put("TR_CD", TR_CD);
        map.put("TR_NM", TR_NM);
        map.put("REG_NB", REG_NB);
        map.put("PPL_NB", PPL_NB);
        map.put("USE_YN", USE_YN);
        map.put("CO_CD", CO_CD);
        map.put("EMP_CD", EMP_CD);

        List<SGtradeDTO> sgtradeList = new ArrayList<>();
        try {
            sgtradeList = stradeService.sgtradeSearchList(map);
        } catch (Exception e) {
            log.error("sgtradeSearchList Controller error : " +
                    "TR_CD={}, TR_NM={}, REG_NB={}, PPL_NB={}, USE_YN={}, CO_CD={}, EMP_CD={}, errorMessage={}",
                    TR_CD,TR_NM,REG_NB,PPL_NB,USE_YN,CO_CD,EMP_CD,e.getMessage());
        }
        return new ResponseEntity<>(sgtradeList, HttpStatus.OK);
    }

    // 전체 금융 거래처 리스트 출력 및 검색 결과 출력
    @GetMapping("getSFtradeList")
    public ResponseEntity<List<SFtradeDTO>> sftradeSearchList(
            @RequestParam(name = "TR_CD", required = false) String TR_CD,
            @RequestParam(name = "TR_NM", required = false) String TR_NM,
            @RequestParam(name = "BA_NB_TR", required = false) String BA_NB_TR,
            @RequestParam(name = "USE_YN", required = false) String USE_YN) {
        log.info("금융 거래처 리스트 출력 Controller");

        // 사원이 속한 회사 코드, 사원 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));
        String EMP_CD = String.valueOf(claims.get("EMP_CD"));

        System.out.println("CO_CD : " + CO_CD);
        System.out.println("EMP_CD : " + EMP_CD);

        // 검색 조건들
        Map<String, String> map = new HashMap<>();
        map.put("TR_CD", TR_CD);
        map.put("TR_NM", TR_NM);
        map.put("BA_NB_TR", BA_NB_TR);
        map.put("USE_YN", USE_YN);
        map.put("CO_CD", CO_CD);
        map.put("EMP_CD", EMP_CD);

        List<SFtradeDTO> sftradeList = new ArrayList<>();
        try {
            sftradeList = stradeService.sftradeSearchList(map);
        } catch (Exception e) {
            log.error("sgtradeSearchList Controller error : " +
                            "TR_CD={}, TR_NM={}, BA_NB_TR={}, USE_YN={}, CO_CD={}, EMP_CD={}, errorMessage={}",
                    TR_CD,TR_NM,BA_NB_TR,USE_YN,CO_CD,EMP_CD,e.getMessage());
        }
        return new ResponseEntity<>(sftradeList, HttpStatus.OK);
    }

    // 거래처 권한 리스트
    @GetMapping("stradeRollManageSearchList")
    public ResponseEntity<List<StradeRollManageDTO>> stradeRollManageSearchList(
            @RequestParam(name = "TR_CD", required = false) String TR_CD)  {
        log.info("stradeRollManageSearchListController : TR_CD={}", TR_CD);

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        Map<String, String> map = new HashMap<>();
        map.put("TR_CD", TR_CD);
        map.put("CO_CD", CO_CD);

        List<StradeRollManageDTO> stradeRollManageList = new ArrayList<>();

        try {
            stradeRollManageList = stradeService.stradeRollManageSearchList(map);
        } catch (Exception e) {
            log.error("stradeRollManageSearchList Error : TR_CD={}, errorMessage={}", TR_CD, e.getMessage());
        }
        return new ResponseEntity<>(stradeRollManageList, HttpStatus.OK);
    }

    // 거래처 데이터 1건 입력
    @PostMapping("stradeInsert")
    public ResponseEntity<String> stradeInsert(@RequestBody SGFtradeDTO sgftradeDTO)  {
        log.info("stradeInsertController : sgftradeDTO={}", sgftradeDTO);

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            sgftradeDTO.setCO_CD(CO_CD);
            stradeService.stradeInsert(sgftradeDTO);
        } catch (Exception e) {
            log.error("stradeInsertController Error : SGFtradeDTO={}, errorMessage={}", sgftradeDTO, e.getMessage());
        }
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    // 일반 거래처 데이터 1건 출력
    @GetMapping("sgtradeDetail")
    public ResponseEntity<SGtradeDTO> sgtradeDetail(
            @RequestParam(name = "TR_CD", required = false) String TR_CD) {
        log.info("sgtradeDetailController 실행");

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        Map<String, String> map = new HashMap<>();
        map.put("CO_CD", CO_CD);
        map.put("TR_CD", TR_CD);

        SGtradeDTO sgtradeInfo = null;
        try {
            sgtradeInfo = stradeService.sgtradeDetail(map);
        } catch (Exception e) {
            log.error("sgtradeDetailController Error : TR_CD={}, errorMessage={}", TR_CD, e.getMessage());
        }

        return new ResponseEntity<>(sgtradeInfo, HttpStatus.OK);
    }

    // 금융 거래처 데이터 1건 출력
    @GetMapping("sftradeDetail")
    public ResponseEntity<SFtradeDTO> sftradeDetail(
            @RequestParam(name = "TR_CD", required = false) String TR_CD) {
        log.info("sftradeDetailController 실행");

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        Map<String, String> map = new HashMap<>();
        map.put("CO_CD", CO_CD);
        map.put("TR_CD", TR_CD);

        SFtradeDTO sftradeInfo = null;
        try {
            sftradeInfo = stradeService.sftradeDetail(map);
        } catch (Exception e) {
            log.error("sftradeDetailController Error : TR_CD={}, errorMessage={}", TR_CD, e.getMessage());
        }

        return new ResponseEntity<>(sftradeInfo, HttpStatus.OK);
    }

    // 거래처 권한 관리
    // 거래처 권한 1건 등록
    @PostMapping("stradeRollManageInsert")
    public ResponseEntity<String> stradeRollManageInsert(@RequestBody StradeRollManageDTO stradeRollManageDTO)  {
        log.info("stradeRollManageInsertController 실행");

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            stradeRollManageDTO.setCO_CD(CO_CD);
            stradeService.stradeRollManageInsert(stradeRollManageDTO);
        } catch (Exception e) {
            log.error("stradeRollManageInsertController Error : stradeRollManageDTO={}, errorMessage={}", stradeRollManageDTO, e.getMessage());
        }
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    // 거래처 권한 1건 업데이트
    @PostMapping("stradeRollManageUpdate")
    public ResponseEntity<String> stradeRollManageUpdate(@RequestBody StradeRollManageDTO stradeRollManageDTO)  {
        log.info("stradeRollManageUpdateController");

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            stradeRollManageDTO.setCO_CD(CO_CD);
            stradeService.stradeRollManageUpdate(stradeRollManageDTO);
        } catch (Exception e) {
            log.error("stradeRollManageUpdateController Error : stradeRollManageDTO={}, errorMessage={}", stradeRollManageDTO, e.getMessage());
        }
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    // 사원코드도움
    // 거래처 권한 리스트
    @GetMapping("empCodeHelpList")
    public ResponseEntity<List<EmpCodeHelpDTO>> empCodeHelpList(EmpCodeHelpListDTO empCodeHelpListDTO)  {
        log.info("empCodeHelpListController");

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        List<EmpCodeHelpDTO> empCodeHelpList = new ArrayList<>();

        try {
            empCodeHelpListDTO.setCO_CD(CO_CD);
            empCodeHelpList = stradeService.empCodeHelpList(empCodeHelpListDTO);
        } catch (Exception e) {
            log.error("empCodeHelpListController Error : empCodeHelpList={}, errorMessage={}", empCodeHelpList, e.getMessage());
        }
        return new ResponseEntity<>(empCodeHelpList, HttpStatus.OK);
    }

    // 거래처 데이터 업데이트 (일반 거래처, 금융 거래처)
    @PostMapping("stradeUpdate")
    public ResponseEntity<String> stradeUpdate(@RequestBody SGFtradeDTO sgftradeDTO)  {
        log.info("stradeUpdateController 출력 : sgftradeDTO={}", sgftradeDTO);

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            sgftradeDTO.setCO_CD(CO_CD);
            stradeService.stradeUpdate(sgftradeDTO);
        } catch (Exception e) {
            log.error("stradeUpdateController Error : SGFtradeDTO={}, errorMessage={}", sgftradeDTO, e.getMessage());
        }
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    // 쿠키에서 사원 정보 가져오기
    public Claims getUserInfo(HttpServletRequest request) {
        String username = null;
        String CO_CD = null;

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

            return claims;

        } catch (Exception e) {
            log.error("getUserInfoError - Token validation failed: " + e.getMessage());
        }
        return null;
    }

    // 일반 거래처 데이터 1건 입력
//    @PostMapping("sgtradeInsert")
//    public ResponseEntity<String> sgtradeInsert(@RequestBody SGtradeDTO sgtradeDTO)  {
//        System.out.println("sgtradeInsertController 출력" + sgtradeDTO);
//
//        // 사원이 속한 회사 코드
//        Claims claims = getUserInfo(request);
//        String CO_CD = String.valueOf(claims.get("CO_CD"));
//
//        try {
//            sgtradeDTO.setCO_CD(CO_CD);
//            stradeService.sgtradeInsert(sgtradeDTO);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println("eeeeeeeeeeeeeeeeee" + sgtradeDTO);
//        return new ResponseEntity<>("입력완료", HttpStatus.OK);
//    }

    // 일반 거래처 데이터 업데이트
//    @PostMapping("sgtradeUpdate")
//    public ResponseEntity<String> sgtradeUpdate(@RequestBody SGtradeDTO sgtradeDTO)  {
//        System.out.println("sgtradeUpdateController 출력 : " + sgtradeDTO);
//
//        // 사원이 속한 회사 코드
//        Claims claims = getUserInfo(request);
//        String CO_CD = String.valueOf(claims.get("CO_CD"));
//        System.out.println("CO_CD : " + CO_CD);
//        System.out.println("sgtradeDTO-TR : " + sgtradeDTO.getTR_CD());
//
//        try {
//            sgtradeDTO.setCO_CD(CO_CD);
//            System.out.println("sgtradeDTO : " + sgtradeDTO);
//            stradeService.sgtradeUpdate(sgtradeDTO);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println("sgtradeUpdate : " + sgtradeDTO);
//        return new ResponseEntity<>("입력완료", HttpStatus.OK);
//    }
}
