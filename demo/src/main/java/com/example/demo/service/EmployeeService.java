package com.example.demo.service;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.dao.EmployeeDao;
import com.example.demo.dto.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional(readOnly = true)
@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private HttpServletRequest request;

    public UserDTO findByUsername(String username) {
        UserDTO loginUserDTO = null;
        try {
        //사용자 계정 정보 확인
            loginUserDTO = employeeDao.userSelect(username);
            //loginUserDTO.setPASSWORD(this.bCryptPasswordEncoder.encode(loginUserDTO.getPASSWORD()));
        } catch (Exception e) {
            log.error("loginUserDTOService Error : loginUserDTO={}, errorMessage={}",loginUserDTO,e.getMessage());
        }
        return loginUserDTO;
    }

    //사원 리스트 출력
    public List<EmployeeDTO> employeeSearchList(Map<String, Object> map) {
        log.info("employeeSearchListService 실행");
        List<EmployeeDTO> employeeList = new ArrayList<>();
        try {
            employeeList = employeeDao.employeeSearchList(map);
        } catch (Exception e) {
            log.error("sgtradeSearchListService Error : employeeList={}, errorMessage={}",employeeList,e.getMessage());
        }
        return employeeList;
    }

    //사업장 리스트 출력
    public List<WorkplaceDTO> selectWorkplaceSearch(String CO_CD) {
        log.info("selectWorkplaceSearchService 실행");
        List<WorkplaceDTO> workplaceList = new ArrayList<>();
        try {
            workplaceList = employeeDao.selectWorkplaceSearch(CO_CD);
        } catch (Exception e) {
            log.error("selectWorkplaceSearchService Error : workplaceList={}, errorMessage={}",workplaceList,e.getMessage());
        }
        return workplaceList;
    }

    //회사 리스트 출력
    public List<CompanyDTO> companySearchList() {
        log.info("companySearchListService 실행");
        List<CompanyDTO> companyList = new ArrayList<>();
        try {
            companyList = employeeDao.companySearchList();
        } catch (Exception e) {
            log.error("companySearchListService Error : companyList={}, errorMessage={}",companyList,e.getMessage());
        }
        return companyList;
    }

    //사원 상세 테이터 1건 출력
    public EmployeeDTO employeeDetail(EmployeeDTO employeeDTO) {
        log.info("employeeDetailService");
        EmployeeDTO employeeInfo = null;
        try {
            employeeInfo = employeeDao.employeeDetail(employeeDTO);
        } catch (Exception e) {
            log.error("employeeDetailService Error : employeeInfo={}, errorMessage={}",employeeInfo,e.getMessage());
        }
        return employeeInfo;
    }

    // 사업장별 사원 사번 존재 여부
    public Boolean employeeEmpCDInWorkplace(Map<String, String> map) {
        log.info("employeeEmpCDInWorkplaceService");
        String employeeEmpCD = null;
        try {
            employeeEmpCD = employeeDao.employeeEmpCDInWorkplace(map);
        } catch (Exception e) {
            log.error("employeeEmpCDInWorkplaceService Error : employeeEmpCD={}, errorMessage={}",employeeEmpCD,e.getMessage());
        }
        if (employeeEmpCD != null) {
            return true;
        } else {
            return false;
        }
    }

    // 회사내 사원 로그인ID 존재 여부
    public Boolean employeeUsernameInCompany(String username) {
        log.info("employeeUsernameInCompanyService");
        String employeeUsername = null;
        try {
            employeeUsername = employeeDao.employeeUsernameInCompany(username);
        } catch (Exception e) {
            log.error("employeeUsernameInCompanyService Error : employeeUsername={}, errorMessage={}",employeeUsername,e.getMessage());
        }
        if (employeeUsername != null) {
            return true;
        } else {
            return false;
        }
    }

    // 회사내 EmailID 존재 여부
    public Boolean employeeEmailInCompany(String EmailID) {
        log.info("employeeEmailInCompanyService");
        String employeeEmailID = null;
        try {
            employeeEmailID = employeeDao.employeeEmailInCompany(EmailID);
        } catch (Exception e) {
            log.error("employeeEmailInCompanyService Error : employeeEmailID={}, errorMessage={}",employeeEmailID,e.getMessage());
        }
        if (employeeEmailID != null) {
            return true;
        } else {
            return false;
        }
    }

    //신규 사원 데이터 1건 입력
    public void employeeInsert(EmployeeDTO employeeDTO) {
        log.info("employeeInsertService 실행");
        try {
            int row = employeeDao.employeeInsert(employeeDTO);
            log.info("입력된 행 " + row);
        } catch (Exception e) {
            log.error("employeeInsertService Error : employeeDTO={}, errorMessage={}",employeeDTO,e.getMessage());
        }
    }

    //특정 사원 데이터 비 활성화
    public void employeeRemove(EmployeeDTO employeeDTO) {
        log.info("employeeRemoveService 실행");
        try {
            int row = employeeDao.employeeRemove(employeeDTO);
            log.info("입력된 행 " + row);
        } catch (Exception e) {
            log.error("employeeRemoveService Error : employeeDTO={}, errorMessage={}",employeeDTO,e.getMessage());
        }
    }

    //특정 사원 데이터 정보 갱신
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class, SQLException.class})
    public void employeeUpdate(EmployeeDTO employeeDTO) {
        log.info("employeeUpdateService 실행");
        int row = employeeDao.employeeUpdate(employeeDTO);
        log.info("입력된 행 " + row);
        if (employeeDTO.getPASSWORD() != null) {
            employeeDao.employeeRollUpdate(employeeDTO);
        }
    }

    public LoginUserInfoDTO loginUserInfo(){
        Claims claims = getUserInfo();
        LoginUserInfoDTO loginUserInfoDTO = null;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEMP_CD(String.valueOf(claims.get("EMP_CD")));
        employeeDTO.setCO_CD(String.valueOf(claims.get("CO_CD")));
        employeeDTO.setDIV_CD(String.valueOf(claims.get("DIV_CD")));
        employeeDTO.setDEPT_CD(String.valueOf(claims.get("DEPT_CD")));
        try {
            loginUserInfoDTO = employeeDao.loginUserInfo(employeeDTO);
            log.info("사용자정보:"+loginUserInfoDTO);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return loginUserInfoDTO;
    }



    public Claims getUserInfo() {
        String username = null;
        try {
            String header = request.getHeader(JwtProperties.HEADER_STRING);
            header = URLDecoder.decode(header, "UTF-8");

            String token = header.replace(JwtProperties.TOKEN_PREFIX, "");


            SecretKey key = Keys.hmacShaKeyFor(JwtProperties.getSecretKey());

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            username = String.valueOf(claims.get("username"));


            return claims;

        } catch (Exception e) {
            log.error("Token validation failed: " + e.getMessage());
        }
        return null;
    }
}
