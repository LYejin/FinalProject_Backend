package com.example.demo.controller;


import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.CompanyService;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system/admin/groupManage/")
@CrossOrigin("http://localhost:3000")
public class CompanyController {
    @Autowired
    private CompanyService companyService;


}
