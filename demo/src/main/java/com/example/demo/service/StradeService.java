package com.example.demo.service;

import com.example.demo.dao.StradeDao;
import com.example.demo.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional(readOnly = true)
@Service
public class StradeService {

    @Autowired
    private StradeDao stradeDao;

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class, SQLException.class})
    // 거래처 등록 Insert
    public void stradeInsert(SGFtradeDTO sgftradeDTO) {
        log.info("stradeInsertService 실행");
        int row = 0;
        if (sgftradeDTO.getTR_FG().equals("1")) {
            log.info("일반 거래처 INSERT 실행");
            row = stradeDao.stradeInsert(sgftradeDTO);
            row += stradeDao.gtradeInsert(sgftradeDTO);
        } else if (sgftradeDTO.getTR_FG().equals("3")) {
            log.info("금융 거래처 INSERT 실행");
            row = stradeDao.stradeInsert(sgftradeDTO);
            row += stradeDao.ftradeInsert(sgftradeDTO);
        }
        log.info("stradeInsert row : {}", row);
    }

    // 전체 일반 거래처 리스트 출력 및 검색 결과 출력
    public List<SGtradeDTO> sgtradeSearchList(Map<String, String> map) {
        log.info("sgtradeSearchListService 실행");
        List<SGtradeDTO> sgtradeList = new ArrayList<>();
        try {
            sgtradeList = stradeDao.sgtradeSearchList(map);
            log.info("결과 sgtradeList={}", sgtradeList);
        } catch (Exception e) {
            log.error("sgtradeSearchListService Error : sgtradeList={}, errorMessage={}",sgtradeList,e.getMessage());
        }
        return sgtradeList;
    }

    // 전체 일반 거래처 리스트 출력 및 검색 결과 출력
    public List<SFtradeDTO> sftradeSearchList(Map<String, String> map) {
        log.info("sftradeSearchListService 실행");
        List<SFtradeDTO> sftradeList = new ArrayList<>();
        try {
            sftradeList = stradeDao.sftradeSearchList(map);
            log.info("결과 sftradeList={}", sftradeList);
        } catch (Exception e) {
            log.error("sftradeSearchListService Error : sftradeList={}, errorMessage={}",sftradeList,e.getMessage());
        }
        return sftradeList;
    }

    // 일반 거래처 데이터 1건 조회
    public SGtradeDTO sgtradeDetail(Map<String, String> map) {
        log.info("sgtradeDetailService 실행");
        SGtradeDTO sgtradeInfo = null;
        try {
            sgtradeInfo = stradeDao.sgtradeDetail(map);
            log.info("결과 sgtradeInfo={}", sgtradeInfo);
        } catch (Exception e) {
            log.error("sgtradeDetailService Error : sgtradeInfo={}, errorMessage={}",sgtradeInfo,e.getMessage());
        }
        return sgtradeInfo;
    }

    // 금융 거래처 데이터 1건 조회
    public SFtradeDTO sftradeDetail(Map<String, String> map) {
        log.info("sftradeDetailService 실행");
        SFtradeDTO sftradeInfo = null;
        try {
            sftradeInfo = stradeDao.sftradeDetail(map);
            log.info("결과 sftradeInfo={}", sftradeInfo);
        } catch (Exception e) {
            log.error("sftradeDetailService Error : sftradeInfo={}, errorMessage={}",sftradeInfo,e.getMessage());
        }
        return sftradeInfo;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class, SQLException.class})
    // 거래처 데이터 갱신
    public void stradeUpdate(SGFtradeDTO sgftradeDTO) {
        log.info("sgtradeUpdateService 실행");
        int row = 0;
        if (sgftradeDTO.getTR_FG().equals("1")) {
            log.info("일반 거래처 UPDATE 실행");
            row = stradeDao.stradeUpdate(sgftradeDTO);
            row += stradeDao.gtradeUpdate(sgftradeDTO);
        } else if (sgftradeDTO.getTR_FG().equals("3")) {
            log.info("금융 거래처 UPDATE 실행");
            row = stradeDao.stradeUpdate(sgftradeDTO);
            row += stradeDao.ftradeUpdate(sgftradeDTO);
        }
        log.info("sgtradeUpdate row : {}", row);
    }


    // 각 거래처 권한 리스트
    public List<StradeRollManageDTO> stradeRollManageSearchList(Map<String, String> map) {
        log.info("stradeRollManageSearchListService 실행");
        List<StradeRollManageDTO> stradeRollManageList = new ArrayList<>();
        try {
            stradeRollManageList = stradeDao.stradeRollManageSearchList(map);
            log.info("결과 stradeRollManageList={}", stradeRollManageList);
        } catch (Exception e) {
            log.error("stradeRollManageSearchListService Error : stradeRollManageList={}, errorMessage={}",stradeRollManageList,e.getMessage());
        }
        return stradeRollManageList;
    }

    // 거래처 권한 관리 insert
    // 거래처 등록 Insert
    public void stradeRollManageInsert(StradeRollManageDTO stradeRollManageDTO) {
        log.info("stradeRollManageInsertService 실행");
        int row = 0;
        try {
            row = stradeDao.stradeRollManageInsert(stradeRollManageDTO);
        } catch (Exception e) {
            log.error("stradeRollManageInsertService Error : stradeRollManageDTO={}, errorMessage={}",stradeRollManageDTO,e.getMessage());
        }
        log.info("stradeRollManageInsert row : {}", row);
    }

    // 거래처 권한 관리 update
    public void stradeRollManageUpdate(StradeRollManageDTO stradeRollManageDTO) {
        log.info("stradeRollManageUpdateService 실행");
        int row = 0;
        try {
            row = stradeDao.stradeRollManageUpdate(stradeRollManageDTO);
        } catch (Exception e) {
            log.error("stradeRollManageUpdateService Error : stradeRollManageDTO={}, errorMessage={}",stradeRollManageDTO,e.getMessage());
        }
        log.info("stradeRollManageUpdate row : {}", row);
    }


    // 사원코드도움 모달창 list
    public List<EmpCodeHelpDTO> empCodeHelpList(EmpCodeHelpListDTO empCodeHelpListDTO) {
        log.info("empCodeHelpListService 실행");
        List<EmpCodeHelpDTO> empCodeHelpList = new ArrayList<>();
        try {
            empCodeHelpList = stradeDao.empCodeHelpList(empCodeHelpListDTO);
            //log.info("결과 empCodeHelpList={}", empCodeHelpList);
        } catch (Exception e) {
            log.error("empCodeHelpListService Error : empCodeHelpList={}, errorMessage={}",empCodeHelpList,e.getMessage());
        }
        return empCodeHelpList;
    }

    // 부서코드도움 모달창 list
    public List<DepartmentDTO> deptCodeHelpList(String CO_CD, String TR_CD) {
        log.info("deptCodeHelpListService 실행");
        List<DepartmentDTO> deptCodeHelpList = new ArrayList<>();
        try {
            deptCodeHelpList = stradeDao.deptCodeHelpList(CO_CD, TR_CD);
            //log.info("결과 deptCodeHelpList={}", deptCodeHelpList);
        } catch (Exception e) {
            log.error("deptCodeHelpListService Error : deptCodeHelpList={}, errorMessage={}",deptCodeHelpList,e.getMessage());
        }
        return deptCodeHelpList;
    }

    // 거래처코드도움 모달창 list
    public List<StradeCodeHelpDTO> stradeCodeHelpList(StradeCodeHelpSearchDTO stradeCodeHelpSearchDTO) {
        log.info("stradeCodeHelpListService 실행");
        List<StradeCodeHelpDTO> stradeCodeHelpList = new ArrayList<>();

        try {
            stradeCodeHelpList = stradeDao.stradeCodeHelpList(stradeCodeHelpSearchDTO);
            //log.info("결과 stradeCodeHelpList={}", stradeCodeHelpList);
        } catch (Exception e) {
            log.error("StradeCodeHelpListService Error : stradeCodeHelpList={}, errorMessage={}",stradeCodeHelpList,e.getMessage());
        }
        return stradeCodeHelpList;
    }

    //    @Transactional
//    거래처 데이터 1건 입력 프로시저
//    public void sgtradeInsert(SGtradeDTO sgtradeDTO) {
//        System.out.println("sgtradeInsertService 실행");
//        try {
//            System.out.println("sgtradeInsert : " + sgtradeDTO);
//            int row = stradeDao.insertStradeAndGtradeProcedure(sgtradeDTO);
//            System.out.println("입력된 행 " + row);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    // 일반 거래처 데이터 갱신
//    public void sgtradeUpdate(SGtradeDTO sgtradeDTO) {
//            System.out.println("sgtradeUpdateService 실행");
//            try {
//                int row  = stradeDao.sgtradeUpdate(sgtradeDTO);
//                System.out.println("sgtradeUpdate row : " + row);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//    }
}
