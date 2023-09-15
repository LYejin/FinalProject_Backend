package com.example.demo.dao;

import com.example.demo.dto.FunTypeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FunTypeDao {

    public String dupCheck(String CASH_CD);
    public List<FunTypeDTO> searchRow(FunTypeDTO funTypeDTO);

    public void fundTypeInsert(FunTypeDTO funTypeDTO);

    public void fundTypeUpdate(FunTypeDTO funTypeDTO);

    public void fundTypeDelete(List<String> checkList);

}
