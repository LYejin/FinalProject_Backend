package com.example.demo.exception;

import com.example.demo.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

    // AccessToken 만료되어 새로운 refreshToken 발급해야함
    @ExceptionHandler(ExpireAccessTokenException.class)
    private ResponseEntity<ErrorResponseDTO> handleExpireAccessTokenException(ErrorResponseDTO errorResponseDTO) {
        log.info("handleExpireAccessTokenException 실행");

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .status(-5)
                .message("AccessToken 만료")
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    // refreshToken 만료되어 새로운 AccessToken, refreshToken 발급해야함
    @ExceptionHandler(ExpireRefreshTokenException.class)
    private ResponseEntity<ErrorResponseDTO> handleExpireRefreshTokenException(ExpireRefreshTokenException ex, HttpServletResponse response) {
        log.info("handleExpireRefreshTokenException 실행");

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .status(40300)
                .message("RefreshToken 만료")
                .build();

        Cookie refreshCookie = new Cookie("refreshToken", null);
        refreshCookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
        refreshCookie.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
        response.addCookie(refreshCookie);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
