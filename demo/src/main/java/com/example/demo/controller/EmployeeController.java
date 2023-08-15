package com.example.demo.controller;


import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("system/user/groupManage/employee")
@CrossOrigin("http://localhost:3000")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    // 회원가입 추후 삭제 예정
    @PostMapping ("join")
    public ResponseEntity<?> join(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("회원가입");
        employeeDTO.setPASSWORD("aaa");
        employeeDTO.setPASSWORD(bCryptPasswordEncoder.encode(employeeDTO.getPASSWORD()));
        //사용자 권한 데이를 세팅
        employeeService.save(employeeDTO);
        return new ResponseEntity<>("회원가입완료", HttpStatus.OK);
    }

    // 전체 사원 리스트 출력 및 검색 결과 출력
    @PostMapping("getList")
    public ResponseEntity<List<EmployeeDTO>> employeeSearchList(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("리스트 출력");
        List<EmployeeDTO> employeeList = employeeService.employeeSearchList(employeeDTO);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    // 전체 사원 리스트 출력 및 검색 결과 출력
    @PostMapping("empInsert")
    public ResponseEntity<String> employeeInsert(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("employeeInsertController 출력");
        employeeService.employeeInsert(employeeDTO);
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }
}
