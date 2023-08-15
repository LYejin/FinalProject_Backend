package com.example.demo.service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    //사원 리스트 출력
    public List<EmployeeDTO> employeeSearchList(EmployeeDTO employeeDTO) {
        System.out.println("employeeSearchListService 실행");
        System.out.println(employeeDTO.getUSERNAME());
        List<EmployeeDTO> employeeList = employeeDao.employeeSearchList(employeeDTO);
        System.out.println(employeeList);
        return employeeList;
    }

    //사원 상세 테이터 1건 출력
    public EmployeeDTO employeeDetail(EmployeeDTO employeeDTO) {
        EmployeeDTO employeeInfo = employeeDao.employeeDetail(employeeDTO);
        return employeeInfo;
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
        int row = employeeDao.employeeUpdate(employeeDTO);
        System.out.println("입력된 행 " + row);
    }
}
