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
    public List<CompanyDTO> companySelectAll(){
        List<CompanyDTO> companyDTOS = null;
        try {
            companyDTOS = companyDao.companySelectAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return companyDTOS;
    }

    public  CompanyDTO companyDetail(String co_CD){
        CompanyDTO companyDTO = null;
        try {
            companyDTO = companyDao.companyDetail(co_CD);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return companyDTO;
    }

    //추가
    public void companyInsert(CompanyDTO companyDTO){
        try {
            companyDao.companyInsert(companyDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //갱신
    public void companyUpdate(CompanyDTO companyDTO){

        try {
            companyDao.companyInsert(companyDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //삭제
    public void companyRemove(String CO_CD){

        try {
            companyDao.companyRemove(CO_CD);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


}
