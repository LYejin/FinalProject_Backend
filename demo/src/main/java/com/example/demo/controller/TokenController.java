package com.example.demo.controller;

import com.example.demo.config.auto.PrincipalDetails;
import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ExpireRefreshTokenException;
import com.example.demo.service.EmployeeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/account/user/token/")
@CrossOrigin("http://localhost:3000")
public class TokenController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("getAccessToken")
    private ResponseEntity<String> handlAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = URLDecoder.decode(extractRefreshToken(request), "UTF-8").replace("Bearer ", "");
        log.info("리플레쉬 토큰" + refreshToken);

        if (refreshToken != null && jwtRefreshTokenIsValid(refreshToken, response)) {
            String newAccessToken = generateNewAccessTokenFromRefreshToken(refreshToken);
            log.info("★뉴토큰(컨트롤러)★"+newAccessToken);
            response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + newAccessToken);

            return new ResponseEntity<>("New AccessToken 발급", HttpStatus.OK);
        }

        if (response.getStatus() == 40300) {
            throw new ExpireRefreshTokenException("RefreshToken 만료", response);
        }
        return new ResponseEntity<>("New AccessToken 발급", HttpStatus.OK);
    }

    @GetMapping("logoutRemoveRefreshToken")
    private ResponseEntity<String> logoutRemoveRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie refreshCookie = new Cookie("refreshToken", null);
        refreshCookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
        refreshCookie.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
        response.addCookie(refreshCookie);

        return new ResponseEntity<>("RefreshToken 삭제 완료", HttpStatus.OK);
    }

    private boolean jwtAccessTokenIsExpired(String token) {
        SecretKey key = Keys.hmacShaKeyFor(JwtProperties.getSecretKey());
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    private String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    //리플레쉬 토큰 유효성 검사
    private boolean jwtRefreshTokenIsValid(String refreshToken, HttpServletResponse response) throws IOException {
        try {
            SecretKey key = Keys.hmacShaKeyFor(JwtProperties.getSecretKey());
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(refreshToken);
            return true;
        } catch (Exception e) {
            log.error("RefreshToken 만료 TokenController :" + e.getMessage());
            response.setStatus(40300); // RefreshToken 만료시 status
            return false;
        }
    }

    //엑세스 코드 신규발급
    private String generateNewAccessTokenFromRefreshToken(String refreshToken) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(JwtProperties.getSecretKey());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            String newAccessToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME))
                    .signWith(key)
                    .compact();

            return newAccessToken;
        } catch (Exception e) {
            return null;
        }
    }

    //리플레쉬 토큰 발급 유무 확인
    private void handleExpiredAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = URLDecoder.decode(extractRefreshToken(request), "UTF-8").replace("Bearer ", "");
        log.info("리플레쉬 토큰" + refreshToken);

        if (refreshToken != null && jwtRefreshTokenIsValid(refreshToken, response)) {

            String newAccessToken = generateNewAccessTokenFromRefreshToken(refreshToken);
            log.info("★뉴토큰(컨트롤러)★"+newAccessToken);
            response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + newAccessToken);
        }
    }
    //엑세스 코드 유효기간 문제 없으면 실행되는 코드
    private void processValidAccessToken(String username) {
        if (username != null) {
            UserDTO user = employeeService.findByUsername(username);

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails,
                    null,
                    principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
