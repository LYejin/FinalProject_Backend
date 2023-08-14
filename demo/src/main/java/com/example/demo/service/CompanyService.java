package com.example.demo.service;

import com.example.demo.dao.CompanyDao;
import com.example.demo.dao.EmployeeDao;
import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkplaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;

    //검색
    public List<CompanyDTO> select(){
        return null;
    }

    //추가
    public void save(){

    }

    //갱신
    public void update(){

    }

    //삭제
    public void delete(){

    }


}
