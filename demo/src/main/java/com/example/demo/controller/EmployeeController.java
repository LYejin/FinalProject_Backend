package com.example.demo.controller;


import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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

    // 사원 상세 테이터 1건 출력
    @PostMapping("empDetail")
    public ResponseEntity<EmployeeDTO> employeeDetail(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("employeeDetail 출력");
        System.out.println("employeeDTO : " + employeeDTO);
        EmployeeDTO employeeInfo = employeeService.employeeDetail(employeeDTO);
        return new ResponseEntity<>(employeeInfo, HttpStatus.OK);
    }

    // 신규 사원 데이터 1건 입력
    @PostMapping("empInsert")
    @ResponseBody
    public ResponseEntity<String> employeeInsert(@RequestPart(value = "image", required=false) MultipartFile image, @RequestPart(value = "userData") EmployeeDTO employeeDTO) throws IOException {
        System.out.println("employeeInsertController 출력");
        String photoImg = null;
        if (image != null) {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] photoEncode = encoder.encode(image.getBytes());
            photoImg = new String(photoEncode, "UTF8");
        }
        employeeDTO.setPIC_FILE_ID(photoImg);
        employeeService.employeeInsert(employeeDTO);
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    // 특정 사원 데이터 비 활성화
    @PostMapping("empRemove")
    public ResponseEntity<String> employeeRemove(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("employeeRemoveController 출력");
        employeeService.employeeRemove(employeeDTO);
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    // 특정 사원 데이터 정보 갱신
    @PostMapping("empUpdate")
    public ResponseEntity<String> employeeUpdate(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("employeeUpdateController 출력");
        employeeService.employeeUpdate(employeeDTO);
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }
}
