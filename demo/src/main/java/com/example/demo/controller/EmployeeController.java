package com.example.demo.controller;


import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.WorkplaceDTO;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("system/user/groupManage/employee")
@CrossOrigin("http://localhost:3000")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    // 전체 사원 리스트 출력 및 검색 결과 출력
    @GetMapping("getList")
    public ResponseEntity<List<EmployeeDTO>> employeeSearchList(
            @RequestParam(name = "CO_CD", required = false) String CO_CD,
            @RequestParam(name = "ENRL_FG", required = false) String[] ENRL_FG,
            @RequestParam(name = "NAME", required = false) String NAME) {
        System.out.println("사원 리스트 출력");
        Map<String,Object> map = new HashMap<>();
        map.put("CO_CD",CO_CD);
        map.put("NAME",NAME);
        if (ENRL_FG != null) {
            String[] enrlList = new String[ENRL_FG.length];
            for (int i = 0; i < ENRL_FG.length; i++) {
                String enrl = ENRL_FG[i];
                if (enrl.equals("재직")) {
                    enrlList[i] = "0";
                } else if (enrl.equals("휴직")) {
                    enrlList[i] = "1";
                } else if (enrl.equals("퇴직")) {
                    enrlList[i] = "2";
                }
            }
            map.put("ENRL_FG",enrlList);
        } else {
           map.put("ENRL_FG",ENRL_FG);
        }

        List<EmployeeDTO> employeeList = employeeService.employeeSearchList(map);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }


    @GetMapping("getWorkplace")
    //사업장 리스트 출력
    public List<WorkplaceDTO> selectWorkplaceSearch(@RequestParam(value="CO_CD", required = false) String CO_CD) {
        System.out.println("selectWorkplaceSearchController 실행");
        System.out.println(CO_CD);
        List<WorkplaceDTO> workplaceList = employeeService.selectWorkplaceSearch(CO_CD);
        System.out.println(workplaceList);
        return workplaceList;
    }


    // 전체 사원 리스트 출력 및 검색 결과 출력
    @GetMapping("getCompanyList")
    public ResponseEntity<List<CompanyDTO>> employeeSearchList() {
        System.out.println("리스트 출력");
        List<CompanyDTO> companyList = employeeService.companySearchList();
        return new ResponseEntity<>(companyList, HttpStatus.OK);
    }

    // 사원 상세 테이터 1건 출력
    @PostMapping("empDetail")
    public ResponseEntity<EmployeeDTO> employeeDetail(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("employeeDetail 출력");
        System.out.println(employeeDTO);
        EmployeeDTO employeeInfo = employeeService.employeeDetail(employeeDTO);
        return new ResponseEntity<>(employeeInfo, HttpStatus.OK);
    }

    // 신규 사원 데이터 1건 입력
    @PostMapping("empInsert")
    @ResponseBody
    public ResponseEntity<String> employeeInsert(@RequestPart(value = "image", required=false) MultipartFile image, @RequestPart(value = "userData") EmployeeDTO employeeDTO) throws IOException {
        System.out.println("employeeInsertController 출력"+employeeDTO);
        String photoImg = null;
        if (image != null) {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] photoBytes = image.getBytes();
            String base64Image = encoder.encodeToString(photoBytes);  // 이미지를 Base64로 인코딩
            String mimeType = image.getContentType();  // 이미지의 MIME 타입 가져오기
            String dataUri = "data:" + mimeType + ";base64," + base64Image;  // "data:" URI 스킴을 포함한 Base64 데이터 생성
            photoImg = dataUri;
        }
        System.out.println(photoImg);
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
    public ResponseEntity<String> employeeUpdate(@RequestPart(value = "image", required=false) MultipartFile image, @RequestPart(value = "userData") EmployeeDTO employeeDTO) throws IOException {
        System.out.println("employeeUpdateController 출력");
        String photoImg = null;
        if (image != null) {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] photoBytes = image.getBytes();
            String base64Image = encoder.encodeToString(photoBytes);  // 이미지를 Base64로 인코딩
            String mimeType = image.getContentType();  // 이미지의 MIME 타입 가져오기
            String dataUri = "data:" + mimeType + ";base64," + base64Image;  // "data:" URI 스킴을 포함한 Base64 데이터 생성
            photoImg = dataUri;
        }
        System.out.println(photoImg);
        System.out.println(employeeDTO);
        employeeDTO.setPIC_FILE_ID(photoImg);
        employeeService.employeeUpdate(employeeDTO);
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }
}
