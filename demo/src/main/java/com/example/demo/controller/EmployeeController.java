package com.example.demo.controller;


import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system/user/groupManage/")
@CrossOrigin("http://localhost:3000")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    @PostMapping ("join")
    public ResponseEntity<?> join(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("회원가입");
        employeeDTO.setPassword("aaa");
        employeeDTO.setPassword(bCryptPasswordEncoder.encode(employeeDTO.getPassword()));
        //사용자 권한 데이를 세팅
        employeeService.save(employeeDTO);
        return new ResponseEntity<>("회원가입완료", HttpStatus.OK);
    }
}
