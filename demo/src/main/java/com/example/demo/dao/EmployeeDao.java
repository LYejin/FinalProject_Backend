package com.example.demo.dao;

import com.example.demo.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeDao {

    public UserDTO userSelect(String userName);

    //사원 리스트 출력
    public List<EmployeeDTO> employeeSearchList(Map<String,Object> map);

    //사업장 목록 가져오기
    public List<WorkplaceDTO> selectWorkplaceSearch(String CO_CD);

    //사원 상세 테이터 1건 출력
    public EmployeeDTO employeeDetail(EmployeeDTO employeeDTO);

    //신규 사원 데이터 1건 입력
    public int employeeInsert(EmployeeDTO employeeDTO);

    //특정 사원 데이터 비 활성화
    public int employeeRemove(EmployeeDTO employeeDTO);

    //특정 사원 데이터 정보 갱신
    public int employeeUpdate(EmployeeDTO employeeDTO);

    // 회사 리스트
    List<CompanyDTO> companySearchList();

    void employeeRollUpdate(EmployeeDTO employeeDTO);

    String employeeEmpCDInWorkplace(Map<String, String> map);

    String employeeUsernameInCompany(String username);

    String employeeEmailInCompany(String emailID);

    public LoginUserInfoDTO loginUserInfo(EmployeeDTO employeeDTO);
}
