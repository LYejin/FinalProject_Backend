package com.example.demo.controller;


import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.LoginUserInfoDTO;
import com.example.demo.dto.WorkplaceDTO;
import com.example.demo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
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
        log.info("employeeSearchListController 실행");

        // 검색 조건들
        Map<String, Object> map = new HashMap<>();
        map.put("CO_CD", CO_CD);
        map.put("NAME", NAME);

        List<EmployeeDTO> employeeList = new ArrayList<>();

        try {
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
                map.put("ENRL_FG", enrlList);
            } else {
                map.put("ENRL_FG", ENRL_FG);
            }
            employeeList = employeeService.employeeSearchList(map);
        } catch (Exception e) {
            log.error("employeeSearchListController Error : employeeList={}, errorMessage={}", employeeList, e.getMessage());
        }
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("getWorkplace")
    //사업장 리스트 출력
    public List<WorkplaceDTO> selectWorkplaceSearch(@RequestParam(value = "CO_CD", required = false) String CO_CD) {
        log.info("selectWorkplaceSearchController 실행");

        List<WorkplaceDTO> workplaceList = new ArrayList<>();

        try {
            workplaceList = employeeService.selectWorkplaceSearch(CO_CD);
        } catch (Exception e) {
            log.error("selectWorkplaceSearchController Error : CO_CD={}, errorMessage={}", CO_CD, e.getMessage());
        }
        return workplaceList;
    }

    // 사업장별 사원 사번 존재 여부
    @GetMapping("getEmpCDInWorkplace")
    public ResponseEntity<Boolean> employeeEmpCDInWorkplace(
            @RequestParam(name = "CO_CD") String CO_CD,
            @RequestParam(name = "EMP_CD") String EMP_CD) {
        log.info("employeeEmpCDInWorkplaceController 실행");

        Map<String, String> map = new HashMap<>();
        map.put("CO_CD", CO_CD);
        map.put("EMP_CD", EMP_CD);

        Boolean employeeEmpCD = false;

        try {
            employeeEmpCD = employeeService.employeeEmpCDInWorkplace(map);
        } catch (Exception e) {
            log.error("employeeEmpCDInWorkplaceController Error : CO_CD={}, EMP_CD={}, errorMessage={}", CO_CD, EMP_CD, e.getMessage());
        }
        return new ResponseEntity<>(employeeEmpCD, HttpStatus.OK);
    }

    // 회사 내 사원 로그인ID 존재 여부
    @GetMapping("getUsernameInCompany")
    public ResponseEntity<Boolean> employeeUsernameInCompany(
            @RequestParam(name = "USERNAME") String username) {
        log.info("employeeUsernameInCompanyController 출력");

        Boolean employeeUsernameYN = false;

        try {
            employeeUsernameYN = employeeService.employeeUsernameInCompany(username);
        } catch (Exception e) {
            log.error("employeeUsernameInCompanyController Error : username={}, errorMessage={}", username, e.getMessage());
        }
        return new ResponseEntity<>(employeeUsernameYN, HttpStatus.OK);
    }

    // 회사 내 EmailID 존재 여부
    @GetMapping("getEmailInCompany")
    public ResponseEntity<Boolean> employeeEmailInCompany(
            @RequestParam(name = "EMAIL_ADD") String EmailID) {
        log.info("employeeEmailInCompanyController 실행");

        Boolean employeeEmailID = false;

        try {
            employeeEmailID = employeeService.employeeEmailInCompany(EmailID);
        } catch (Exception e) {
            log.error("employeeSearchListController Error : EmailID={}, errorMessage={}", EmailID, e.getMessage());
        }
        return new ResponseEntity<>(employeeEmailID, HttpStatus.OK);
    }

    // 전체 사원 리스트 출력 및 검색 결과 출력
    @GetMapping("getCompanyList")
    public ResponseEntity<List<CompanyDTO>> employeeSearchList() {
        log.info("employeeSearchListController 실행");

        List<CompanyDTO> companyList = new ArrayList<>();

        try {
            companyList = employeeService.companySearchList();
        } catch (Exception e) {
            log.error("employeeSearchListController Error : companyList={}, errorMessage={}", companyList, e.getMessage());
        }
        return new ResponseEntity<>(companyList, HttpStatus.OK);
    }

    // 사원 상세 테이터 1건 출력
    @PostMapping("empDetail")
    public ResponseEntity<EmployeeDTO> employeeDetail(@RequestBody EmployeeDTO employeeDTO) {
        log.info("employeeDetailController 실행");

        EmployeeDTO employeeInfo = null;

        try {
            employeeInfo = employeeService.employeeDetail(employeeDTO);
        } catch (Exception e) {
            log.error("employeeDetailController Error : employeeDTO={}, errorMessage={}", employeeDTO, e.getMessage());
        }
        return new ResponseEntity<>(employeeInfo, HttpStatus.OK);
    }

    // 신규 사원 데이터 1건 입력
    @PostMapping("empInsert")
    @ResponseBody
    public ResponseEntity<String> employeeInsert(@RequestPart(value = "image", required = false) MultipartFile image, @RequestPart(value = "userData") EmployeeDTO employeeDTO) throws IOException {
        log.info("employeeInsertController 출력" + employeeDTO);

        String photoImg = null;
        String dataUrlInsert = null;

        try {
            // 비밀번호 암호화 bCrypt
            String password = bCryptPasswordEncoder.encode(employeeDTO.getPASSWORD());
            employeeDTO.setPASSWORD(password);


            if (image != null) {
                Base64.Encoder encoder = Base64.getEncoder();
                byte[] photoBytes = image.getBytes();
                String base64Image = encoder.encodeToString(photoBytes);  // 이미지를 Base64로 인코딩
                String mimeType = image.getContentType();  // 이미지의 MIME 타입 가져오기
                dataUrlInsert = "data:" + mimeType + ";base64," + base64Image;  // "data:" URI 스킴을 포함한 Base64 데이터 생성
                photoImg = dataUrlInsert;
            }

            employeeDTO.setPIC_FILE_ID(photoImg);
            employeeService.employeeInsert(employeeDTO);
        } catch (Exception e) {
            log.error("employeeInsertController Error : image={}, employeeDTO={}, errorMessage={}", image, employeeDTO, e.getMessage());
        }
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    // 특정 사원 데이터 비 활성화
    @PostMapping("empRemove")
    public ResponseEntity<String> employeeRemove(@RequestBody EmployeeDTO employeeDTO) {
        log.info("employeeRemoveController 출력");

        try {
            employeeService.employeeRemove(employeeDTO);
        } catch (Exception e) {
            log.error("employeeRemoveController Error : employeeDTO={}, errorMessage={}", employeeDTO, e.getMessage());
        }
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    // 특정 사원 데이터 정보 갱신
    @PostMapping("empUpdate")
    public ResponseEntity<String> employeeUpdate(@RequestPart(value = "image", required = false) MultipartFile image, @RequestPart(value = "userData") EmployeeDTO employeeDTO) throws IOException {
        log.info("employeeUpdateController 실행");

        String photoImg = null;
        String dataURL = null;

        try {
            if (employeeDTO.getENRL_FG() != null && employeeDTO.getENRL_FG().equals("2")) {
                // 비밀번호 암호화 bCrypt
                employeeDTO.setUSE_FG("0");
                employeeDTO.setUSER_YN("0");
            }

            if (employeeDTO.getPASSWORD() != null) {
                // 비밀번호 암호화 bCrypt
                String password = bCryptPasswordEncoder.encode(employeeDTO.getPASSWORD());
                employeeDTO.setPASSWORD(password);
            }


            if (image != null) {
                Base64.Encoder encoder = Base64.getEncoder();
                byte[] photoBytes = image.getBytes();
                String base64Image = encoder.encodeToString(photoBytes);  // 이미지를 Base64로 인코딩
                String mimeType = image.getContentType();  // 이미지의 MIME 타입 가져오기
                dataURL = "data:" + mimeType + ";base64," + base64Image;  // "data:" URI 스킴을 포함한 Base64 데이터 생성
                photoImg = dataURL;
            }
            employeeDTO.setPIC_FILE_ID(photoImg);
            employeeService.employeeUpdate(employeeDTO);
        } catch (Exception e) {
            log.error("employeeUpdateController Error : image={},employeeDTO={}, errorMessage={}", image, employeeDTO, e.getMessage());
        }
        return new ResponseEntity<>("입력완료", HttpStatus.OK);
    }

    @GetMapping("loginUserInfo")
    public ResponseEntity<?> loginUserInfo() {

        LoginUserInfoDTO loginUserInfoDTO = null;
        try {
            loginUserInfoDTO = employeeService.loginUserInfo();
        } catch (Exception e) {
            log.error("employeeRemoveController Error : employeeDTO={}, errorMessage={}", e.getMessage());
        }
        return new ResponseEntity<LoginUserInfoDTO>(loginUserInfoDTO, HttpStatus.OK);
    }


}
