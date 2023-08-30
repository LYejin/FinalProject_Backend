package com.example.demo.dao;

import com.example.demo.dto.ChangeHistoryDTO;
import com.example.demo.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ChangeHistoryDao {

    public Map<String, String> getEMP_CDAndDIV_NM(String USERNAME);
    public void changeHistoryInset(ChangeHistoryDTO changeHistoryDTO);
}
