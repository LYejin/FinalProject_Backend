package com.example.demo.dao;

import com.example.demo.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StradeDao {

    // 일반 거래처 프로시저
    int insertStradeAndGtradeProcedure(SGtradeDTO sgtradeDTO);

    // 일반 거래처 리스트
    List<SGtradeDTO> sgtradeSearchList(Map<String, String> map);

    // 금융 거래처 리스트
    List<SFtradeDTO> sftradeSearchList(Map<String, String> map);

    // 일반 거래처 디테일 정보
    SGtradeDTO sgtradeDetail(Map<String, String> map);

    // 금융 거래처 디테일 정보
    SFtradeDTO sftradeDetail(Map<String, String> map);


//    int sgtradeUpdate(SGtradeDTO sgtradeDTO);

    // 거래처 업데이트
    int stradeUpdate(SGFtradeDTO stradeDTO);

    // 일반 거래처 업데이트
    int gtradeUpdate(SGFtradeDTO sgftradeDTO);

    // 금융 거래처 업데이트
    int ftradeUpdate(SGFtradeDTO sgftradeDTO);

    // 거래처 Insert
    int stradeInsert(SGFtradeDTO sgftradeDTO);

    // 일반 거래처 Insert
    int gtradeInsert(SGFtradeDTO sgftradeDTO);

    // 금융 거래처 Insert
    int ftradeInsert(SGFtradeDTO sgftradeDTO);

    // 거래처 권한 Insert
    int stradeRollManageInsert(StradeRollManageDTO stradeRollManageDTO);

    // 거래처 권한 리스트
   List<StradeRollManageDTO> stradeRollManageSearchList(Map<String, String> map);

   // 거래처 권한 업데이트
    int stradeRollManageUpdate(StradeRollManageDTO stradeRollManageDTO);

    // 사원코드도움 리스트
    List<EmpCodeHelpDTO> empCodeHelpList(EmpCodeHelpListDTO empCodeHelpDTO);
}
