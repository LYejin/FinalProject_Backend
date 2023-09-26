package com.example.demo.dao;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.WorkplaceDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DepartmentDao {
    //부서 목록 가져오기
    List<DepartmentDTO> getDepartmentHierarchy(String CO_CD);

    //부서 상세정보 가져오기
    public DepartmentDTO selectDepartmentInfoByDEPTCD(String DEPT_CD);

    //부서 등록(추가)
    public int insertDepartment(DepartmentDTO departmentDTO);

    //부서 수정
    public int updateDepartment(DepartmentDTO departmentDTO);

    //부서 삭제
    public int deleteDepartment(String DEPT_CD);

}
