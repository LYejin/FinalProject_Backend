package com.example.demo.config.aop;//package com.example.demo.aop;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.dto.*;
import com.example.demo.service.ChangeHistoryService;
import com.example.demo.service.CompanyService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.WorkplaceService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private EmployeeService employeeService;
    @Autowired
    private WorkplaceService workplaceService;
    @Autowired
    private HttpServletRequest request;


    @Pointcut("execution(* com.example.demo.service.CompanyService.*(..)) || " +
            "execution(* com.example.demo.service.EmployeeService.*(..)) || " +
            "execution(* com.example.demo.service.WorkplaceService.*(..))")
    public void serviceMethods() {
    }

    @Pointcut("execution(* *Update*(*)) || execution(* *Insert*(*)) || execution(* *Remove*(*))")
    public void updateInsertRemoveMethods() {
    }

    @Pointcut("execution(* *Update*(..))")
    public void updateBefore() {
    }

    private ChangeHistorySearchDTO changeHistorySearchDTO;
    private ChangeHistoryDTO changeHistoryDTO;

    private Claims claims;


    private CompanyDTO b_companyDTO;
    private EmployeeDTO b_employeeDTO;
    private WorkplaceDTO b_workplaceDTO;

    private CompanyDTO a_companyDTO;
    private EmployeeDTO a_employeeDTO;
    private WorkplaceDTO a_workplaceDTO;

    private String CO_CD;
    private String DIV_CD;


    @Before("serviceMethods() && updateBefore()")
    public void doUpdateBefore(JoinPoint joinPoint) {
        b_companyDTO = null;
        b_workplaceDTO = null;
        b_employeeDTO = null;
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        if (methodName.contains("Company")) {
            b_companyDTO = companyService.companyDetail(((CompanyDTO) joinPoint.getArgs()[0]).getCO_CD());
        } else if (methodName.contains("Workplace")) {
            WorkplaceDTO workplaceDTO = (WorkplaceDTO) joinPoint.getArgs()[0];
            Map<String, String> params = new HashMap<>();
            params.put("DIV_CD", workplaceDTO.getDIV_CD());
            params.put("CO_CD", workplaceDTO.getCO_CD());
            b_workplaceDTO = workplaceService.selectWorkplaceInfoByDIVCD(params);

        } else if (methodName.contains("Employee")) {
            b_employeeDTO = employeeService.employeeDetail(((EmployeeDTO) joinPoint.getArgs()[0]));
        }

        log.info("{} is start", methodName);
    }

    @AfterReturning(pointcut = "serviceMethods() && updateInsertRemoveMethods()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("AOP★★★★★★★★★★★★★★★★★★" );
        claims = null;
        a_companyDTO = null;
        a_employeeDTO = null;
        a_workplaceDTO = null;

        CO_CD = null;
        DIV_CD = null;

        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        claims = getUserInfo();
        changeHistoryDTO = new ChangeHistoryDTO();


        System.out.println("AOP★★★★★★★★★★★★★★★★★★" + methodName);


        changeHistoryDTO.setEMP_CD(String.valueOf(claims.get("EMP_CD")));
        changeHistoryDTO.setCO_CD(String.valueOf(claims.get("CO_CD")));
        changeHistoryDTO.setCH_CATEGORY(findCH_CATEGORY(methodName));
        changeHistoryDTO.setCH_DIVISION(findCH_DIVISION(methodName));
        changeHistoryDTO.setCH_IM(getCH_IM(changeHistoryDTO.getCH_CATEGORY(), changeHistoryDTO.getCH_DIVISION()));
        changeHistoryDTO.setCH_DT(currentTime());
        changeHistoryDTO.setCH_IP(getLoopbackIPv4());
        changeHistoryDTO.setCH_NM(getCH_NM(String.valueOf(claims.get("username"))));

        setChangeHistoryDTO(args, methodName);


//        log.info("CO_CD: {}", CO_CD);
//        log.info("DIV_CD: {}", DIV_CD);
        log.info("서치 데이터"+changeHistorySearchDTO);
        Map<String, String> resultMap = getCHD_TARGET(changeHistorySearchDTO);
        changeHistoryDTO.setCHD_TARGET_CO_NM(resultMap.get("CHD_TARGET_CO_NM"));
        changeHistoryDTO.setCHD_TARGET_NM(resultMap.get("CHD_TARGET_NM"));

        //changeHistory 데이터 사입
        changeHistoryInsert(changeHistoryDTO);

//        log.info("a_companyDTO: {}", a_companyDTO);
//        log.info("b_companyDTO: {}", b_companyDTO);
//        log.info("a_employeeDTO: {}", a_employeeDTO);
//        log.info("b_employeeDTO: {}", b_employeeDTO);
//        log.info("a_workplaceDTO: {}", a_workplaceDTO);
//        log.info("b_workplaceDTO: {}", b_workplaceDTO);

        List<ChangeHistoryDetailDTO> changeHistoryDetailDTOS = null;
        if (changeHistoryDTO.getCH_DIVISION().equals("수정")) {
            if (methodName.contains("Company")) {
                changeHistoryDetailDTOS = changeHistoryDetailInfo(b_companyDTO, a_companyDTO);
            } else if (methodName.contains("Workplace")) {
                changeHistoryDetailDTOS = changeHistoryDetailInfo(b_workplaceDTO, a_workplaceDTO);
            } else if (methodName.contains("Employee")) {
                System.out.println("사원!!!!!!!!!!!!!!!!");
                changeHistoryDetailDTOS = changeHistoryDetailInfo(b_employeeDTO, a_employeeDTO);
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("changeHistoryDTO", changeHistoryDTO);
        params.put("changeHistoryDetailList", changeHistoryDetailDTOS);
        changeHistoryService.changeHistoryDetailInset(params);


//        log.info("CO_CD: {}", CO_CD);
//        log.info("DIV_CD: {}", DIV_CD);
//        log.info("CH Data: {}", changeHistoryDTO);
//        log.info("CH Data: {}", changeHistorySearchDTO);
//        log.info("changeHistoryDetailDTOS: {}", changeHistoryDetailDTOS);
//        log.info("{} is start", methodName);
//        log.info("{} is Finish", methodName);


    }


    public String findCH_DIVISION(String methodName) {
        String CH_CATEGORY = null;

        if (methodName.contains("Insert")) {
            CH_CATEGORY = "추가";
        } else if (methodName.contains("Update")) {
            CH_CATEGORY = "수정";
        } else if (methodName.contains("Remove")) {
            CH_CATEGORY = "삭제";
        }

        return CH_CATEGORY;
    }

    public String findCH_CATEGORY(String methodName) {
        String CH_DIVISION = null;

        if (methodName.contains("Company")) {
            CH_DIVISION = "회사";
        } else if (methodName.contains("Workplace")) {
            CH_DIVISION = "사업장";
        } else if (methodName.contains("Employee")) {
            CH_DIVISION = "사원";
        }

        return CH_DIVISION;
    }

    public String getCH_IM(String CH_CATEGORY, String CH_DIVISION) {
        return CH_CATEGORY + "가(이) " + CH_DIVISION + "되었습니다";
    }

    public String currentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public String getLoopbackIPv4() {
        InetAddress loopback = InetAddress.getLoopbackAddress();
        return loopback.getHostAddress();
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
            System.out.println("이름:" + username);


            return claims;

        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
        }
        return null;
    }

    public String getCH_NM(String USERNAME) {
        String CH_NM = null;
        try {
            System.out.println("aop" + USERNAME);
            CH_NM = changeHistoryService.CH_NM_Select(USERNAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return CH_NM;
    }

    public void setChangeHistoryDTO(Object[] args, String methodName) {
        if (args.length > 0) {
            Object argument = args[0];
            if (argument instanceof CompanyDTO) {
                a_companyDTO = (CompanyDTO) args[0];
                changeHistorySearchDTO = setChangeHistoryValues("CO_CD", a_companyDTO.getCO_CD(), "Company", "CO_NM","null");
            } else if (argument instanceof EmployeeDTO) {
                a_employeeDTO = (EmployeeDTO) args[0];
                changeHistorySearchDTO = setChangeHistoryValues("USERNAME", a_employeeDTO.getUSERNAME(), "Employee", "KOR_NM","null");
            } else if (argument instanceof WorkplaceDTO) {
                a_workplaceDTO = (WorkplaceDTO) args[0];
                changeHistorySearchDTO = setChangeHistoryValues("DIV_CD", a_workplaceDTO.getDIV_CD(), "Workplace", "DIV_NM",a_workplaceDTO.getCO_CD());
            } else if (methodName.contains("Company") && argument instanceof String) {
                CO_CD = (String) args[0];
                changeHistorySearchDTO = setChangeHistoryValues("CO_CD", CO_CD, "Company", "CO_NM","null");
            } else if (methodName.contains("Workplace") && argument instanceof Map<?, ?>) {
                Map<String, String> params = (Map<String, String>) args[0];
                changeHistorySearchDTO = setChangeHistoryValues("DIV_CD", params.get("DIV_CD"), "Workplace", "DIV_NM", params.get("CO_CD"));
            }
        }
    }

    public ChangeHistorySearchDTO setChangeHistoryValues(String columnName, String identifyValue, String tableName, String chdTarget, String CO_CD) {
        ChangeHistorySearchDTO changeHistorySearchDTO = new ChangeHistorySearchDTO();
        changeHistorySearchDTO.setIDENTIFY_COLUMN_NAME(columnName);
        changeHistorySearchDTO.setIDENTIFY_VALUE(identifyValue);
        changeHistorySearchDTO.setTABLENAME(tableName);
        changeHistorySearchDTO.setCHD_TARGET(chdTarget);
        changeHistorySearchDTO.setCH_DT(changeHistoryDTO.getCH_DT());
        if(tableName.equals("Workplace")){
            changeHistorySearchDTO.setCO_CD(CO_CD);
        }
        return changeHistorySearchDTO;

    }

    public Map<String, String> getCHD_TARGET(ChangeHistorySearchDTO changeHistorySearchDTO) {
        Map<String, String> resultMap = null;
        log.info("검색:" + changeHistorySearchDTO);
        try {
            resultMap = changeHistoryService.getCHD_TARGET(changeHistorySearchDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // log.info("검색된 데이터:" + resultMap);

        return resultMap;
    }

    public void changeHistoryInsert(ChangeHistoryDTO changeHistoryDTO) {

        changeHistoryService.changeHistoryInsert(changeHistoryDTO);
    }

    public List<ChangeHistoryDetailDTO> changeHistoryDetailInfo(Object b_object, Object a_object) {
        List<ChangeHistoryDetailDTO> changeDetails = new ArrayList<>();

        // 클래스 필드 반복
        Class<?> DTO = b_object.getClass();
        Field[] fields = DTO.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object b_value = field.get(b_object);
                Object a_value = field.get(a_object);

                if ((a_value != null && !a_value.equals(b_value) && !field.getName().equals("columnsToUpdate"))) {
                    // 필드 값이 다른 경우 또는 a_value가 null이 아닌 경우에만 처리
                    ChangeHistoryDetailDTO detailDTO = new ChangeHistoryDetailDTO();
                    detailDTO.setEMP_CD(String.valueOf(claims.get("EMP_CD")));
                    detailDTO.setCO_CD(String.valueOf(claims.get("CO_CD")));
                    detailDTO.setCHD_ITEM(field.getName());
                    detailDTO.setCHD_DT(field.getType().getName().contains("String") ? "String" : "image");
                    //log.info("field.getName()"+field.getName());
                    if (field.getName().equals("PIC_FILE_ID")) {
                        // log.info("사진타냐?????"+field.getName());
                        detailDTO.setCHD_PIC_BT(b_value != null ? b_value.toString() : null);
                        detailDTO.setCHD_PIC_AT(a_value != null ? a_value.toString() : null);
                    } else {
                        detailDTO.setCHD_BT(b_value != null ? b_value.toString() : null);
                        detailDTO.setCHD_AT(a_value != null ? a_value.toString() : null);
                    }

                    changeDetails.add(detailDTO);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return changeDetails;
    }


}



