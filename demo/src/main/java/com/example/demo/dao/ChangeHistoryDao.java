package com.example.demo.dao;

import com.example.demo.dto.ChangeHistoryDTO;
import com.example.demo.dto.ChangeHistoryDetailDTO;
import com.example.demo.dto.ChangeHistorySearchDTO;
import com.example.demo.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChangeHistoryDao {
    public String CH_NM_Select(String USERNAME);
    public Map<String, String>  CHD_TARGET_select(ChangeHistorySearchDTO changeHistorySearchDTO);
    public void changeHistoryInset(ChangeHistoryDTO changeHistoryDTO);

    public void changeHistoryDetailInset(Map<String, Object> params);

    public List<ChangeHistoryDTO> ChangeHistoryList(String CH_CATEGORY);

    public List<ChangeHistoryDTO> ChangeHistorySearch(ChangeHistoryDTO changeHistoryDTO) ;

    public List<ChangeHistoryDetailDTO> ChangeHistoryDetailList(ChangeHistoryDTO changeHistoryDTO);

}
