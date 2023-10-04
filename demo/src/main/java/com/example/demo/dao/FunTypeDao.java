package com.example.demo.dao;

import com.example.demo.dto.FunTypeDTO;
import com.example.demo.dto.FundTypeTreeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FunTypeDao {

    public String dupCheck(Map<String, String> dupMap);
    public List<FunTypeDTO> searchRow(FunTypeDTO funTypeDTO);

    public void fundTypeInsert(FunTypeDTO funTypeDTO);

    public void fundTypeUpdate(FunTypeDTO funTypeDTO);

    public void highFundsNameUpdate(FunTypeDTO funTypeDTO);

    public List<String> highFundsList(Map<String, Object> delectMap);

    public void fundTypeDelete(Map<String, Object> delectMap);

    public List<FundTypeTreeDTO> fundTypeTreeList();

}
