package com.example.demo.config.aop;//package com.example.demo.aop;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.dto.ChangeHistoryDTO;
import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.ChangeHistoryService;
import com.example.demo.service.CompanyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
@Aspect
@Component
public class DemoCommonAspect {


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ChangeHistoryService changeHistoryService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private HttpServletRequest request;


    @Pointcut("execution(* com.example.demo.service.CompanyService.companyRemove(..)) || " +
            "execution(* com.example.demo.service.CompanyService.companyUpdate(..)) || " +
            "execution(* com.example.demo.service.CompanyService.companyInsert(..))")
    private void doExecute() {}

    @AfterReturning(pointcut = "doExecute()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result)  {
        String methodName = joinPoint.getSignature().toShortString();
        CompanyDTO companyDTO = null;
        String CO_CD = null;
        ChangeHistoryDTO changeHistoryDTO = new ChangeHistoryDTO();
        System.out.println("AOP★★★★★★★★★★★★★★★★★★"+methodName);


        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof CompanyDTO){
            companyDTO = (CompanyDTO) joinPoint.getArgs()[0];
            changeHistoryDTO.setCO_NM(getCO_NM(companyDTO.getCO_CD()));
        }else if(args.length > 0 && args[0] instanceof String){
            CO_CD = (String) joinPoint.getArgs()[0];
            changeHistoryDTO.setCO_NM(CO_CD);
        }


        changeHistoryDTO.setCH_CATEGORY(findCH_CATEGORY(methodName));
        changeHistoryDTO.setCH_DIVISION(findCH_DIVISION(methodName));
        changeHistoryDTO.setCH_IM(getCH_IM(changeHistoryDTO.getCH_CATEGORY(), changeHistoryDTO.getCH_DIVISION()));
        changeHistoryDTO.setCH_DT(currentTime());
        changeHistoryDTO.setCU_IP(getLoopbackIPv4());
        changeHistoryDTO.setCU_NM(getCU_NM());


        Map<String, String> map = getEMP_CDAndDIV_NM(changeHistoryDTO.getCU_NM());
        System.out.println(map);
        changeHistoryDTO.setEMP_CD(map.get("EMP_CD"));
        changeHistoryDTO.setDIV_NM(map.get("DIV_NM"));




        log.info("Data: {}", companyDTO);
        log.info("CO_CD: {}", CO_CD);
        log.info("CH Data: {}",changeHistoryDTO);
        log.info("{} is start", methodName);
        log.info("{} is Finish", methodName);

        changeHistoryInset(changeHistoryDTO, companyDTO);
    }

    public String findCH_DIVISION(String methodName){
        String CH_CATEGORY = null;

      if(methodName.contains("Insert")){
          CH_CATEGORY = "추가";
      }else if(methodName.contains("Update")){
          CH_CATEGORY = "수정";
      }else if(methodName.contains("Remove")){
          CH_CATEGORY = "삭제";
      }

        return CH_CATEGORY;
    }
    public String findCH_CATEGORY(String methodName){
        String CH_DIVISION = null;

        if(methodName.contains("Company")){
            CH_DIVISION = "회사";
        }else if(methodName.contains("Workplace")){
            CH_DIVISION = "사업장";
        }else if(methodName.contains("Employee")){
            CH_DIVISION = "사원";
        }

        return CH_DIVISION;
    }

    public String getCH_IM(String CH_CATEGORY, String CH_DIVISION) {
        return CH_CATEGORY+"가 "+CH_DIVISION+"되었습니다";
    }

    public String currentTime () {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public String getLoopbackIPv4() {
        InetAddress loopback = InetAddress.getLoopbackAddress();
        return loopback.getHostAddress();
    }

    public String getCU_NM() {
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
            System.out.println("이름:" + username);
            return username;

        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
        }
        return username;
    }

    public String getCO_NM(String CO_CD){
        String CO_NM = null;
        try {
            CO_NM = companyService.companyNameSelect(CO_CD);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return CO_NM;
    }

    public Map<String, String> getEMP_CDAndDIV_NM(String USERNAME){

        Map<String, String> resultMap = new HashMap<>();
        resultMap = changeHistoryService.getEMP_CDAndDIV_NM(USERNAME);

        System.out.println("map데이터:"+resultMap);

        return  resultMap;
    }
    public void changeHistoryInset(ChangeHistoryDTO changeHistoryDTO, CompanyDTO companyDTO){

        changeHistoryService.changeHistoryInset(changeHistoryDTO);
    }





}



