package com.example.demo.service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkplaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {


    @Autowired
    private EmployeeDao employeeDao;


    public UserDTO findByUsername(String username) {
        //사용자 계정 정보 확인
        UserDTO loginUserDTO = employeeDao.userSelect(username);
        //loginUserDTO.setPASSWORD(this.bCryptPasswordEncoder.encode(loginUserDTO.getPASSWORD()));


        return loginUserDTO;
    }
    //검색
    public List<EmployeeDTO> select(){
        return null;
    }

    //추가
    public void save(EmployeeDTO employeeDTO){

        try{
            employeeDao.employeesave(employeeDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //갱신
    public void update(){

    }

    //삭제
    public void delete(){

    }


}
