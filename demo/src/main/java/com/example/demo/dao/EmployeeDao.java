package com.example.demo.dao;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeDao {

    public UserDTO userSelect(String userName);
    public void employeesave(EmployeeDTO employeeDTO);
}
