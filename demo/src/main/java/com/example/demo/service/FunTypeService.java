package com.example.demo.service;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.dao.FunTypeDao;
import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.FunTypeDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FunTypeService {

    @Autowired
    private FunTypeDao funTypeDao;
    @Autowired
    private HttpServletRequest request;
    public String dupCheck(String CASH_CD){
        String dupValue = null;
        try {
            dupValue = funTypeDao.dupCheck(CASH_CD);
        }catch (Exception e){
            log.error(e.getMessage());
            dupValue = null;
        }
        return dupValue;
    }

    public List<FunTypeDTO> searchRow(FunTypeDTO funTypeDTO){
        List<FunTypeDTO> funTypeDTOS = null;
        try {
            findByInputColumns(funTypeDTO);
            funTypeDTOS = funTypeDao.searchRow(funTypeDTO);
        }catch (Exception e){
            log.error(e.getMessage());
            funTypeDTOS = null;
        }
        return funTypeDTOS;
    }

    public void fundTypeInsert(FunTypeDTO funTypeDTO){
        Claims claims = getUserInfo();
        String logUser = String.valueOf(claims.get("username"));
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            funTypeDTO.setINSERT_DT(logTime());
            funTypeDTO.setINSERT_ID(logUser);
            funTypeDTO.setCO_CD(CO_CD);
            findByInputColumns(funTypeDTO);
            funTypeDao.fundTypeInsert(funTypeDTO);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void fundTypeUpdate(FunTypeDTO funTypeDTO){
        Claims claims = getUserInfo();
        String logUser = String.valueOf(claims.get("username"));
        String CO_CD = String.valueOf(claims.get("CO_CD"));

        try {
            funTypeDTO.setMODIFY_DT(logTime());
            funTypeDTO.setMODIFY_ID(logUser);
            funTypeDTO.setCO_CD(CO_CD);
            findByInputColumns(funTypeDTO);
            funTypeDao.fundTypeUpdate(funTypeDTO);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void fundTypeDelete(List<String> checkList){
        try {
            funTypeDao.fundTypeDelete(checkList);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void findByInputColumns(FunTypeDTO funTypeDTO){
        List<String> columnsToUpdate = new ArrayList<>();

        // DTO의 필드를 반복하며 널이 아닌 데이터가 담긴 필드를 columnsToUpdate 목록에 추가
        //getDeclaredFields() 함수는 해당 클래스에 선언된 모든 필드(멤버 변수)를 배열로 반환
        Field[] fields = funTypeDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 비공개(private) 필드에 접근 가능하도록 설정
            field.setAccessible(true);
            try {
                // 각 필드마다 실제로 담긴 값 가져오기
                Object value = field.get(funTypeDTO);
                // 값이 null이 아닌 경우 컬럼 이름 리스트에 추가
                if (value != null ) {
                    columnsToUpdate.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                // 필드에 접근 예외가 발생하면 무시
            }
        }
        // columnsToUpdate 목록을 설정하고 DAO 메서드를 호출합니다
        funTypeDTO.setColumnsToUpdate(columnsToUpdate);
    }

    public String logTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
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
