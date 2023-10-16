package com.example.demo.dao;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkplaceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WorkplaceDao {

    //사업장 목록 가져오기
    public List<WorkplaceDTO> selectWorkplaceSearch(Map<String,String> map);

    //사업장 상세정보 가져오기
    public WorkplaceDTO selectWorkplaceInfoByDIVCD(Map<String, String> params);


    //사업장 등록(추가)
    public int insertWorkplace(WorkplaceDTO workplaceDTO);

    //사업장 수정
    public int updateWorkplace(WorkplaceDTO workplaceDTO);

    //사업장 삭제
    public int deleteWorkplace(Map<String, String> params);

    public int checkWorkpDuplicate(Map<String, String> params);

}
