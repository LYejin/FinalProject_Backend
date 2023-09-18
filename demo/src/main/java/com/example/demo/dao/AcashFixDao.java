package com.example.demo.dao;

import com.example.demo.dto.AcashFixDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AcashFixDao {

    public List<AcashFixDTO> selectAcashFixSearch(Map<String,String> map);
}
