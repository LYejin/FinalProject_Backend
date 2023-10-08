package com.example.demo.dao;

import com.example.demo.dto.AcashFixDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AcashFixDao {

    //고정자금 불러오기
    public List<AcashFixDTO> selectAcashFixSearch(Map<String,String> map);

    //고정자금 추가
    public int insertAcashFix(AcashFixDTO acashFixDTO);

    //고정자금 수정
    public int updateAcashFix(AcashFixDTO acashFixDTO);

    //고정자금 삭제
    public int deleteAcashFix(Map<String, Object> map);


    // 연도별 금액 합계 조회
    public List<Map<String, Object>> selectYearlyAmounts(Map<String, Object> params);

    // 분기별 금액 합계 조회
    public List<Map<String, Object>> selectQuarterlyAmounts(Map<String, Object> params);

    //월별 금액 합계 조회
    List<Map<String, Object>> selectMonthlyAmounts(Map<String, Object> params);
}
