package com.example.demo.dao;

import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeDao {

    public UserDTO userSelect(String userName);
    public void employeesave(EmployeeDTO employeeDTO);

    //사원 리스트 출력
    public List<EmployeeDTO> employeeSearchList(EmployeeDTO employee);

    //사원 상세 테이터 1건 출력
    public EmployeeDTO employeeDetail(EmployeeDTO employeeDTO);

    //신규 사원 데이터 1건 입력
    public int employeeInsert(EmployeeDTO employeeDTO);

    //특정 사원 데이터 비 활성화
    public int employeeRemove(EmployeeDTO employeeDTO);

    //특정 사원 데이터 정보 갱신
    public int employeeUpdate(EmployeeDTO employeeDTO);

}
