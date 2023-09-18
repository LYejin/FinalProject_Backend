package com.example.demo.dao;

import com.example.demo.dto.AcashFixDTO;
import com.example.demo.dto.WorkplaceDTO;
import org.apache.ibatis.annotations.Mapper;

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
}
