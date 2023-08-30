package com.example.demo.service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkplaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    //사원 리스트 출력
    public List<EmployeeDTO> employeeSearchList(Map<String, Object> map) {
        System.out.println("employeeSearchListService 실행");
        List<EmployeeDTO> employeeList = employeeDao.employeeSearchList(map);
        System.out.println(employeeList);
        return employeeList;
    }

    //사업장 리스트 출력
    public List<WorkplaceDTO> selectWorkplaceSearch(String CO_CD) {
        System.out.println("selectWorkplaceSearchService 실행");
        List<WorkplaceDTO> workplaceList = employeeDao.selectWorkplaceSearch(CO_CD);
        System.out.println(workplaceList);
        return workplaceList;
    }

    //회사 리스트 출력
    public List<CompanyDTO> companySearchList() {
        System.out.println("companySearchListService 실행");
        List<CompanyDTO> companyList = employeeDao.companySearchList();
        return companyList;
    }

    //사원 상세 테이터 1건 출력
    public EmployeeDTO employeeDetail(EmployeeDTO employeeDTO) {
        System.out.println("employeeDetailService");
        EmployeeDTO employeeInfo = employeeDao.employeeDetail(employeeDTO);
        return employeeInfo;
    }

    // 사업장별 사원 사번 존재 여부
    public Boolean employeeEmpCDInWorkplace(Map<String, String> map) {
        System.out.println("employeeEmpCDInWorkplaceService");
        String employeeEmpCD = employeeDao.employeeEmpCDInWorkplace(map);
        if (employeeEmpCD != null) {
            return true;
        } else {
            return false;
        }
    }

    // 회사내 사원 로그인ID 존재 여부
    public Boolean employeeUsernameInCompany(String username) {
        System.out.println("employeeUsernameInCompanyService");
        String employeeUsername = employeeDao.employeeUsernameInCompany(username);
        if (employeeUsername != null) {
            return true;
        } else {
            return false;
        }
    }

    // 회사내 EmailID 존재 여부
    public Boolean employeeEmailInCompany(String EmailID) {
        System.out.println("employeeEmailInCompanyService");
        String employeeEmailID = employeeDao.employeeEmailInCompany(EmailID);
        if (employeeEmailID != null) {
            return true;
        } else {
            return false;
        }
    }

    //신규 사원 데이터 1건 입력
    public void employeeInsert(EmployeeDTO employeeDTO) {
        System.out.println("employeeInsertService 실행");
        int row = employeeDao.employeeInsert(employeeDTO);
        System.out.println("입력된 행 " + row);
    }

    //특정 사원 데이터 비 활성화
    public void employeeRemove(EmployeeDTO employeeDTO) {
        System.out.println("employeeRemove 실행");
        int row = employeeDao.employeeRemove(employeeDTO);
        System.out.println("입력된 행 " + row);
    }

    //특정 사원 데이터 정보 갱신
    public void employeeUpdate(EmployeeDTO employeeDTO) {
        System.out.println("employeeUpdate 실행");
        System.out.println("service"+ employeeDTO);
        int row = employeeDao.employeeUpdate(employeeDTO);
        System.out.println("입력된 행 " + row);
        if (employeeDTO.getPASSWORD() != null) {
            employeeDao.employeeRollUpdate(employeeDTO);
        }
    }
}
