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
import java.sql.Timestamp;
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
        String DEPT_CD = String.valueOf(claims.get("DEPT_CD"));

        // 검색 조건들
        Map<String, Object> map = new HashMap<>();
        map.put("TR_CD", TR_CD);
        map.put("TR_NM", TR_NM);
        map.put("REG_NB", REG_NB);
        map.put("PPL_NB", PPL_NB);
        map.put("USE_YN", USE_YN);
        map.put("CO_CD", CO_CD);
        map.put("EMP_CD", EMP_CD);
        map.put("DEPT_CD", DEPT_CD);

        System.out.println(map);

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
        String DEPT_CD = String.valueOf(claims.get("DEPT_CD"));

        // 검색 조건들
        Map<String, Object> map = new HashMap<>();
        map.put("TR_CD", TR_CD);
        map.put("TR_NM", TR_NM);
        map.put("BA_NB_TR", BA_NB_TR);
        map.put("USE_YN", USE_YN);
        map.put("CO_CD", CO_CD);
        map.put("EMP_CD", EMP_CD);
        map.put("DEPT_CD", DEPT_CD);

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
        String trCd = null;

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            sgftradeDTO.setCO_CD(CO_CD);
            trCd = stradeService.stradeInsert(sgftradeDTO);
        } catch (Exception e) {
            log.error("stradeInsertController Error : SGFtradeDTO={}, errorMessage={}", sgftradeDTO, e.getMessage());
        }
        return new ResponseEntity<>(trCd, HttpStatus.OK);
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
            stradeRollManageDTO.setINSERT_DT(new Timestamp(System.currentTimeMillis()));
            stradeService.stradeRollManageInsert(stradeRollManageDTO);
        } catch (Exception e) {
            log.error("stradeRollManageInsertController Error : stradeRollManageDTO={}, errorMessage={}", stradeRollManageDTO, e.getMessage());
        }
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    // 사원도움모달 거래처 권한 list 등록
    @PostMapping("stradeRollInEmpInsert")
    public ResponseEntity<String> stradeRollInEmpInsert(@RequestBody List<StradeRollManageDTO> list)  {
        log.info("stradeRollInDeptInsertController 실행");
        Map<String, Object> map = new HashMap<String, Object>();

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        for (StradeRollManageDTO srmd : list) {
            srmd.setCO_CD(CO_CD);
        }

        map.put("list", list);

        try {
            for (StradeRollManageDTO srmd : list) {
                System.out.println("--------"+ srmd);
            }
            stradeService.stradeRollInEmpInsert(list);
        } catch (Exception e) {
            log.error("stradeRollInDeptInsertController Error : list={}, errorMessage={}", list, e.getMessage());
        }
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    // 사원도움모달 거래처 권한 list 등록
    @PostMapping("stradeRollInDeptInsert")
    public ResponseEntity<String> stradeRollInDeptInsert(@RequestBody List<StradeRollManageDTO> list)  {
        log.info("stradeRollInDeptInsertController 실행");
        Map<String, Object> map = new HashMap<String, Object>();

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        for (StradeRollManageDTO srmd : list) {
            srmd.setCO_CD(CO_CD);
        }

        map.put("list", list);

        try {
            for (StradeRollManageDTO srmd : list) {
            }
            stradeService.stradeRollInDeptInsert(list);
        } catch (Exception e) {
            log.error("stradeRollInDeptInsertController Error : list={}, errorMessage={}", list, e.getMessage());
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

    // 사원코드도움 모달창 list
    @GetMapping("empCodeHelpList")
    public ResponseEntity<List<EmpCodeHelpDTO>> empCodeHelpList(EmpCodeHelpListDTO empCodeHelpListDTO)  {
        log.info("empCodeHelpListController");
        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));
        System.out.println("CO_CD : " + CO_CD);
        List<EmpCodeHelpDTO> empCodeHelpList = new ArrayList<>();

        try {
            empCodeHelpListDTO.setCO_CD(CO_CD);
            empCodeHelpList = stradeService.empCodeHelpList(empCodeHelpListDTO);
        } catch (Exception e) {
            log.error("empCodeHelpListController Error : empCodeHelpList={}, errorMessage={}", empCodeHelpList, e.getMessage());
        }
        return new ResponseEntity<>(empCodeHelpList, HttpStatus.OK);
    }

    // 부서코드도움 모달창 list
    @GetMapping("deptCodeHelpList")
    public ResponseEntity<List<DepartmentDTO>> deptCodeHelpList(DeptCodeHelpListDTO deptCodeHelpListDTO)  {
        log.info("deptCodeHelpListController");
        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        List<DepartmentDTO> deptCodeHelpList = new ArrayList<>();

        try {
            deptCodeHelpListDTO.setCO_CD(CO_CD);
            deptCodeHelpList = stradeService.deptCodeHelpList(deptCodeHelpListDTO);
        } catch (Exception e) {
            log.error("deptCodeHelpListController Error : deptCodeHelpList={}, errorMessage={}", deptCodeHelpList, e.getMessage());
        }
        return new ResponseEntity<>(deptCodeHelpList, HttpStatus.OK);
    }

    // 거래처코드도움 모달창 list
    @GetMapping("stradeCodeHelpList")
    public ResponseEntity<List<StradeCodeHelpDTO>> stradeCodeHelpList(StradeCodeHelpSearchDTO stradeCodeHelpSearchDTO)  {
        log.info("stradeCodeHelpListController");

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        System.out.println(stradeCodeHelpSearchDTO);
        List<StradeCodeHelpDTO> stradeCodeHelpList = new ArrayList<>();

        try {
            stradeCodeHelpSearchDTO.setCO_CD(CO_CD);
            stradeCodeHelpList = stradeService.stradeCodeHelpList(stradeCodeHelpSearchDTO);
        } catch (Exception e) {
            log.error("stradeCodeHelpListController Error : stradeCodeHelpList={}, errorMessage={}", stradeCodeHelpList, e.getMessage());
        }
        return new ResponseEntity<>(stradeCodeHelpList, HttpStatus.OK);
    }

    // 주류코드도움 모달창 list
    @GetMapping("liquorcodeHelpList")
    public ResponseEntity<List<LiquorcodeHelpListDTO>> liquorcodeHelpList(@RequestParam(value = "VALUE", required = false) String VALUE)  {
        log.info("stradeCodeHelpListController");

        List<LiquorcodeHelpListDTO> liquorcodeHelpList = new ArrayList<>();

        try {
            liquorcodeHelpList = stradeService.liquorcodeHelpList(VALUE);
        } catch (Exception e) {
            log.error("stradeCodeHelpListController Error : liquorcodeHelpList={}, errorMessage={}", liquorcodeHelpList, e.getMessage());
        }
        return new ResponseEntity<>(liquorcodeHelpList, HttpStatus.OK);
    }

    // 금융코드도움 모달창 list
    @GetMapping("financecodeHelpList")
    public ResponseEntity<List<FinancecodeHelpListDTO>> financecodeHelpList(@RequestParam(value = "VALUE", required = false) String VALUE)  {
        log.info("financecodeHelpListController");

        List<FinancecodeHelpListDTO> financecodeHelpList = new ArrayList<>();

        try {
            financecodeHelpList = stradeService.financecodeHelpList(VALUE);
            System.out.println("financecodeHelpList : " + financecodeHelpList);
        } catch (Exception e) {
            log.error("financecodeHelpListController Error : financecodeHelpList={}, errorMessage={}", financecodeHelpList, e.getMessage());
        }
        return new ResponseEntity<>(financecodeHelpList, HttpStatus.OK);
    }

    // 거래처 권한 관리 삭제
    @DeleteMapping("stradeRollManageDelete")
    public ResponseEntity<String> stradeRollManageDelete(@RequestBody StradeRollManageDeleteDTO stradeRollManageDelete)  {
        log.info("stradeRollManageDeleteController");

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));
        stradeRollManageDelete.setCO_CD(CO_CD);
        try {
            stradeService.stradeRollManageDelete(stradeRollManageDelete);
        } catch (Exception e) {
            log.error("stradeRollManageDeleteController Error : stradeRollManageDelete={}, errorMessage={}", stradeRollManageDelete, e.getMessage());
        }
        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }


    // 거래처 삭제
    @DeleteMapping("stradeDelete")
    public ResponseEntity<List<StradeDeleteInfo>> stradeDelete(@RequestBody TrCdListDTO trCdListDTO)  {
        log.info("stradeDeleteController : trCdListDTO={}", trCdListDTO);
        List<StradeDeleteInfo> stradeUseDataList = new ArrayList<>();
        List<StradeDeleteDTO> stradeDeleteList = new ArrayList<>();

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));
        for (String trCd : trCdListDTO.getTR_CD()) {
            StradeDeleteDTO stradeDeleteDTO = new StradeDeleteDTO();
            stradeDeleteDTO.setTR_CD(trCd);
            stradeDeleteDTO.setCO_CD(CO_CD);
            stradeDeleteDTO.setTR_FG(trCdListDTO.getTR_FG());
            stradeDeleteList.add(stradeDeleteDTO);
        }
        try {
            stradeUseDataList = stradeService.stradeDelete(stradeDeleteList);
        } catch (Exception e) {
            log.error("stradeDeleteController Error : stradeDeleteList={}, errorMessage={}", stradeDeleteList, e.getMessage());
        }
        return new ResponseEntity<List<StradeDeleteInfo>>(stradeUseDataList, HttpStatus.OK);
    }


    // 거래처 내 거래처 코드 존재 여부
    @GetMapping("trCdInStrade")
    public ResponseEntity<Boolean> trCdInStrade(@RequestParam(value = "TR_CD") String TR_CD) {
        log.info("trCdInStradeController 실행");

        Boolean trCdID = false;

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            trCdID = stradeService.trCdInStrade(CO_CD, TR_CD);
        } catch (Exception e) {
            log.error("trCdInStradeController Error : trCdID={}, errorMessage={}", trCdID, e.getMessage());
        }
        return new ResponseEntity<>(trCdID, HttpStatus.OK);
    }

    // 주류코드 자동완성 구현
    @GetMapping("financecodeInfo")
    public ResponseEntity<List<FinancecodeDTO>> financecodeInfo(@RequestParam(value = "FINANCE_CD")String financeCD) {
        log.info("financecodeInfoController 실행");

        List<FinancecodeDTO> financeCDList = new ArrayList<>();

        try {
            financeCDList = stradeService.financecodeInfo(financeCD);
        } catch (Exception e) {
            log.error("financecodeInfoController Error : financeCDList={}, errorMessage={}", financeCDList, e.getMessage());
        }
        return new ResponseEntity<>(financeCDList, HttpStatus.OK);
    }

    // 금융코드 자동완성 구현
    @GetMapping("liqcodeInfo")
    public ResponseEntity<List<LiquorcodeDTO>> liqcodeInfo(@RequestParam(value = "LIQ_CD")String liq_CD) {
        log.info("liqcodeInfoController 실행");

        List<LiquorcodeDTO> liqCDList = new ArrayList<>();

        try {
            liqCDList = stradeService.liqcodeInfo(liq_CD);
        } catch (Exception e) {
            log.error("liqcodeInfoController Error : liqCDList={}, errorMessage={}", liqCDList, e.getMessage());
        }
        return new ResponseEntity<>(liqCDList, HttpStatus.OK);
    }

    // 그리드 사원코드 자동완성 구현
    @GetMapping("gridEmpCode")
    public ResponseEntity<String> gridEmpCode(GridEmpCdDTO gridEmpCdDTO) {
        log.info("gridEmpCodeController 실행");

        String gridKorNM = null;

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            gridEmpCdDTO.setCO_CD(CO_CD);
            gridKorNM = stradeService.gridEmpCode(gridEmpCdDTO);
        } catch (Exception e) {
            log.error("gridEmpCodeController Error : gridKorNM={}, errorMessage={}", gridKorNM, e.getMessage());
        }
        return new ResponseEntity<>(gridKorNM, HttpStatus.OK);
    }

    // 그리드 부서코드 자동완성 구현
    @GetMapping("gridDeptCd")
    public ResponseEntity<String> gridDeptCd(GridDeptCdDTO gridDeptCdDTO) {
        log.info("gridDeptCdController 실행");

        String gridDeptNM = null;

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            gridDeptCdDTO.setCO_CD(CO_CD);
            gridDeptNM = stradeService.gridDeptCd(gridDeptCdDTO);
        } catch (Exception e) {
            log.error("gridDeptCdController Error : gridDeptNM={}, errorMessage={}", gridDeptNM, e.getMessage());
        }
        return new ResponseEntity<>(gridDeptNM, HttpStatus.OK);
    }

    // 그리드 부서코드 유효성
    @PostMapping("gridUseDeptCd")
    public ResponseEntity<String> gridUseDeptCd(@RequestBody GridDeptCdDTO gridDeptCdDTO) {
        log.info("gridUseDeptCdController 실행");
        String gridUseDeptCd = null;
        System.out.println("dddddd"+gridDeptCdDTO);

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            gridDeptCdDTO.setCO_CD(CO_CD);
            gridUseDeptCd = stradeService.gridUseDeptCd(gridDeptCdDTO);
        } catch (Exception e) {
            log.error("ggridUseDeptCdController Error : gridUseDeptCd={}, errorMessage={}", gridUseDeptCd, e.getMessage());
        }
        return new ResponseEntity<>(gridUseDeptCd, HttpStatus.OK);
    }

    // 채번 기능
    @GetMapping("getStradeSeq")
    public ResponseEntity<String> getStradeSeq(@RequestParam(value = "TR_FG")String TR_FG) {
        log.info("getStradeSeqController 실행");
        StradeSeqDTO seqDTO = new StradeSeqDTO();

        String stradeSeq = null;

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            seqDTO.setCO_CD(CO_CD);
            seqDTO.setTR_FG(TR_FG);
            stradeSeq = stradeService.getStradeSeq(seqDTO);
        } catch (Exception e) {
            log.error("getStradeSeqController Error : stradeSeq={}, errorMessage={}", stradeSeq, e.getMessage());
        }
        return new ResponseEntity<>(stradeSeq, HttpStatus.OK);
    }

    // 그리드 사원코드 유효성
    @PostMapping("gridUseEmpCd")
    public ResponseEntity<String> gridUseEmpCd(@RequestBody GridEmpCdDTO gridEmpCdDTO) {
        log.info("gridUseEmpCdController 실행");
        String gridEmpCd = null;
        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            gridEmpCdDTO.setCO_CD(CO_CD);
            gridEmpCd = stradeService.gridUseEmpCd(gridEmpCdDTO);
        } catch (Exception e) {
            log.error("gridUseEmpCdController Error : gridEmpCd={}, errorMessage={}", gridEmpCd, e.getMessage());
        }
        return new ResponseEntity<>(gridEmpCd, HttpStatus.OK);
    }

    // 거래처코드 유효성
    @GetMapping("trCdVal")
    public ResponseEntity<Boolean> trCdVal(@RequestParam(value = "TR_CD")String TR_CD) {
        log.info("trCdValController 실행");
        Boolean trCdVal = false;

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            trCdVal = stradeService.trCdVal(CO_CD, TR_CD);
        } catch (Exception e) {
            log.error("trCdValController Error : trCdVal={}, errorMessage={}", trCdVal, e.getMessage());
        }
        return new ResponseEntity<>(trCdVal, HttpStatus.OK);
    }

    // 계좌번호 유효성
    @GetMapping("baNbTrVal")
    public ResponseEntity<Boolean> baNbTrVal(@RequestParam(value = "BA_NB_TR")String BA_NB_TR) {
        log.info("baNbTrValController 실행");
        Boolean baNbTrVal = false;

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            baNbTrVal = stradeService.baNbTrVal(CO_CD, BA_NB_TR);
        } catch (Exception e) {
            log.error("baNbTrValController Error : baNbTrVal={}, errorMessage={}", baNbTrVal, e.getMessage());
        }
        return new ResponseEntity<>(baNbTrVal, HttpStatus.OK);
    }

    // 사업자등록번호 유효성
    @GetMapping("regNbVal")
    public ResponseEntity<Boolean> regNbVal(@RequestParam(value = "REG_NB")String REG_NB) {
        log.info("regNbValController 실행");
        Boolean regNbVal = false;

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            regNbVal = stradeService.regNbVal(CO_CD, REG_NB);
        } catch (Exception e) {
            log.error("regNbValController Error : regNbVal={}, errorMessage={}", regNbVal, e.getMessage());
        }
        return new ResponseEntity<>(regNbVal, HttpStatus.OK);
    }

    // 주민등록번호 유효성
    @GetMapping("pplNbVal")
    public ResponseEntity<Boolean> pplNbVal(@RequestParam(value = "PPL_NB")String PPL_NB) {
        log.info("gridUseDeptCdController 실행");
        Boolean pplNbVal = false;

        // 사원이 속한 회사 코드
        Claims claims = getUserInfo(request);
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            pplNbVal = stradeService.pplNbVal(CO_CD, PPL_NB);
        } catch (Exception e) {
            log.error("ggridUseDeptCdController Error : pplNbVal={}, errorMessage={}", pplNbVal, e.getMessage());
        }
        return new ResponseEntity<>(pplNbVal, HttpStatus.OK);
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
