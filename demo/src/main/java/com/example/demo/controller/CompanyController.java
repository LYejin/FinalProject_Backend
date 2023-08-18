package com.example.demo.controller;


import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
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

@RestController
@RequestMapping("system/admin/groupManage/")
@CrossOrigin("http://localhost:3000")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("CompanySelect")
    public ResponseEntity<?> companySelectAll(){

        List<CompanyDTO> companyDTOS = companyService.companySelectAll();

        return new ResponseEntity<List<CompanyDTO>>(companyDTOS, HttpStatus.OK);
    }


    @GetMapping("CompanyDetail/{co_CD}")
    public ResponseEntity<?> companyDetail(@PathVariable String co_CD){
        System.out.println(co_CD);
        CompanyDTO companyDTO = companyService.companyDetail(co_CD);


        return new ResponseEntity<CompanyDTO>(companyDTO, HttpStatus.OK);
    }


    @PostMapping(value = "CompanyInsert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> companyInsert(CompanyDTO companyDTO) {

        int index = companyDTO.getPIC_FILE_ID().indexOf("data");
        String data = companyDTO.getPIC_FILE_ID().substring(index);

        if(index != -1){
            companyDTO.setPIC_FILE_ID(data);
        }

        System.out.println(index+"이미지확인"+data);
        companyService.companyInsert(companyDTO);

        return new ResponseEntity<String>("데이터 입력 성공", HttpStatus.OK);
    }
    @PutMapping("CompanyUpdate")
    public ResponseEntity<?> companyUpdate(@RequestBody CompanyDTO companyDTO){


        companyService.companyUpdate(companyDTO);

        return  new ResponseEntity<String>("데이터 갱신 성공", HttpStatus.OK);
    }

    @PutMapping("CompanyRemove/{CO_CD}")
    public ResponseEntity<?> companyRemove(@PathVariable(value = "CO_CD") String CO_CD){

        return new ResponseEntity<String>("데이터 비활성화 성공", HttpStatus.OK);

    }



//    @PostMapping("CompanyInsert")
//    public ResponseEntity<String> insert(@RequestBody ){
//        try {
//            System.out.println("insert");
//            System.out.println(emp.toString());
//            empservice.insert(emp);
//            return new ResponseEntity<String>("insert success", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<String>("insert fail", HttpStatus.BAD_REQUEST);
//        }
//
//    }


}
