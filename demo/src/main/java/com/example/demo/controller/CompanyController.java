package com.example.demo.controller;


import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.CommonService;
import com.example.demo.service.CompanyService;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("system/admin/groupManage/")
@CrossOrigin("http://localhost:3000")
public class CompanyController {
    @Autowired
    private CompanyService companyService;



    @PostMapping("CompanySelect")
    public ResponseEntity<?> companySelect(CompanyDTO companyDTO){

        List<CompanyDTO> companyDTOS = companyService.companySelect(companyDTO);

        return new ResponseEntity<List<CompanyDTO>>(companyDTOS, HttpStatus.OK);
    }

    @GetMapping("CompanyDetail/{co_CD}")
    public ResponseEntity<?> companyDetail(@PathVariable String co_CD){

        CompanyDTO companyDTO = companyService.companyDetail(co_CD);
        //System.out.println("상세"+companyDTO);

        return new ResponseEntity<CompanyDTO>(companyDTO, HttpStatus.OK);
    }


    @PostMapping(value = "CompanyInsert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> companyInsert(CompanyDTO companyDTO) {

        System.out.println("삽입"+companyDTO);
        companyService.companyInsert(companyDTO);

        return new ResponseEntity<String>("데이터 입력 성공", HttpStatus.OK);
    }
    @PutMapping(value = "CompanyUpdate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> companyUpdate(CompanyDTO companyDTO){

        System.out.println("갱신"+companyDTO);
        companyService.companyUpdate(companyDTO);

        return  new ResponseEntity<String>("데이터 갱신 성공", HttpStatus.OK);
    }

    @PutMapping("CompanyRemove/{CO_CD}")
    public ResponseEntity<?> companyRemove(@PathVariable(value = "CO_CD") String CO_CD){
        System.out.println("삭제:"+CO_CD);
        companyService.companyRemove(CO_CD);
        return new ResponseEntity<String>("데이터 비활성화 성공", HttpStatus.OK);

    }

    @PostMapping("CompanyDup")
    public ResponseEntity<?> companyDup(CompanyDTO companyDTO){

        System.out.println("타냐?"+companyDTO);
        String dup = companyService.companyDup(companyDTO);
        System.out.println("중복값:"+dup);


        return new ResponseEntity<String>(dup, HttpStatus.OK);
    }








}
