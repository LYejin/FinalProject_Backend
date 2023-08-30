package com.example.demo.dao;

import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyDao {

    //전체 회사 리스트 출력
    public List<CompanyDTO> companySelect(CompanyDTO companyDTO);
    public String companyNameSelect(String CO_CD);

    //회사 상세 테이터 1건 출력
    public CompanyDTO companyDetail(String co_CD);

    //신규 회사 데이터 1건 입력
    public void companyInsert(CompanyDTO companyDTO);

    //특정 회사 데이터 비 활성화
    public void companyRemove(String CO_CD);
    public void workplaceRemove(String CO_CD);
    public void employeeRemove(String CO_CD);

    //특정 회사 데이터 정보 갱신
    public void companyUpdate(CompanyDTO companyDTO);

    public String companyDup(CompanyDTO companyDTO);

    public List<CompanyDTO> CompanySearch(CompanyDTO companyDTO);
}
