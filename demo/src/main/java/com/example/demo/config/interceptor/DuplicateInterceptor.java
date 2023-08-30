package com.example.demo.config.interceptor;

import com.example.demo.dto.CompanyDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class DuplicateInterceptor implements AsyncHandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpServletRequest servletRequest = (HttpServletRequest) request;
//
//        if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
//            Collection<Part> parts = servletRequest.getParts();
//            System.out.println("타냐!!!!!!!!!!!!!!!!!");
//            for (Part part : parts) {
//                if (part.getName().equals("jsonFormData")) { // 이름 변경 가능
//                    try (InputStream inputStream = part.getInputStream();
//                         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//
//                        String jsonData = reader.lines().collect(Collectors.joining());
//
//                        // 출력하여 데이터 확인 (디버깅 용도)
//                        System.out.println("JSON 데이터: " + jsonData);
//
//                        CompanyDTO companyDTO = objectMapper.readValue(jsonData, CompanyDTO.class);
//                        System.out.println("가져온 데이터: " + companyDTO);
//                    } catch (Exception e) {
//                        // 예외 정보 로깅
//                        logger.error("Error while processing multipart/form-data: " + e.getMessage(), e);
//                    }
//                }
//            }
//        }
//
//        MDC.put("requestId", UUID.randomUUID().toString());
//        return true;
//    }









    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("[postHandle]");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception {
        logger.info("[afterCompletion]");
    }


}

