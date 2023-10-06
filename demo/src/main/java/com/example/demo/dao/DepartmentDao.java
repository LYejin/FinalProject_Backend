package com.example.demo.dao;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.dto.DeptEmpListDTO;
import com.example.demo.dto.WorkplaceDTO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

@Mapper
public interface DepartmentDao {

    //부서 목록 가져오기
    List<DepartmentDTO> getDepartmentHierarchy(String CO_CD);

    //부서 상세정보 가져오기
    public DepartmentDTO selectDepartmentInfoByDEPTCD(Map<String, String> params);

    //부서 등록(추가)
    public int insertDepartment(DepartmentDTO departmentDTO);

    //부서 수정
    public int updateDepartment(DepartmentDTO departmentDTO);

    //부서 삭제
    public int deleteDepartment(String DEPT_CD);

    //부서코드 중복검사
    public int checkDepartmentDuplicate(Map<String, String> params);


    //사원목록가져오기
    List<DeptEmpListDTO> selectDeptEmpList(Map<String, String> params);


    //부서삭제 검사
    int countEmployeeWithCondition(Map<String, Object> params);
    int countDepartmentWithCondition(Map<String, Object> params);

    //부서삭제(하위부서 및 사원 사용여부 변경)
    int updateEmployeeUserYNWithDeptCD(Map<String, Object> params);
    int updateEmployeeUserYNWithMDeptCD(Map<String, Object> params);
    int updateDepartmentDeptYN(Map<String, Object> params);

    //부서삭제
    int deleteDepartment(DepartmentRequestDTO departmentRequestDTO);
}
