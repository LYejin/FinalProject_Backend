package com.example.demo.config.jwt;

import com.example.demo.config.auto.PrincipalDetails;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.EmployeeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;



// 인가 필터 : 인증-정보(JWT 토큰)를 확인하는 코드
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final EmployeeService employeeService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, EmployeeService employeeService) {
        super(authenticationManager);
        this.employeeService = employeeService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("토큰 인증");
        log.info("토큰값"+request.getHeader("Authorization"));
        String header =request.getHeader(JwtProperties.HEADER_STRING);

        try{
            header = URLDecoder.decode(header, "UTF-8");
        }catch (Exception e){
            log.error(e.getMessage());
        }

        log.info(JwtProperties.HEADER_STRING);
        log.info("header : " + header);

        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(JwtProperties.TOKEN_PREFIX, "");
        log.info("가져온 토큰"+token);

        SecretKey key = Keys.hmacShaKeyFor(JwtProperties.getSecretKey());

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            String username = String.valueOf(claims.get("username"));
            log.info("사용자명 이름:" + username);
            log.info("정보"+String.valueOf(claims.get("DEPT_CD")));
            log.info("유효값"+ jwtAccessTokenIsExpired(token));

            if (jwtAccessTokenIsExpired(token)) {
                handleExpiredAccessToken(request, response);
                return;
            } else {
                processValidAccessToken(username);
            }

        } catch (ExpiredJwtException ex) {
            handleExpiredAccessToken(request, response);
        } catch (Exception e) {
            // Token validation failed, continue without setting authentication
            log.error("Token validation failed: " + e.getMessage());
        }

        chain.doFilter(request, response);
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
            log.error("RefreshToken 만료 :" + e.getMessage());
            response.setStatus(40300);
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
        try {
            String refreshToken = URLDecoder.decode(extractRefreshToken(request), "UTF-8").replace("Bearer ", "");
            log.info("리플레쉬 토큰" + refreshToken);

            if (refreshToken != null && jwtRefreshTokenIsValid(refreshToken, response)) {

                String newAccessToken = generateNewAccessTokenFromRefreshToken(refreshToken);
                log.info("★뉴토큰★" + newAccessToken);
                response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + newAccessToken);
                return;
            }
        } catch (Exception e) {
            log.info("refreshToken :" + e.getMessage());
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
