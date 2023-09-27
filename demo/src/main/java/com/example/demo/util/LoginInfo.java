package com.example.demo.util;

import com.example.demo.config.jwt.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

@Slf4j
public class LoginInfo {


    @Autowired
    private static HttpServletRequest request;
    public static Claims getUserInfo() {
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
