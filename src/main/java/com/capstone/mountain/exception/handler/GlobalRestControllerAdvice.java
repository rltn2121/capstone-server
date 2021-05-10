package com.capstone.mountain.exception.handler;

import com.capstone.mountain.exception.custom.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import java.io.IOException;
import java.net.BindException;
import java.util.HashMap;
import java.util.Map;


//        HttpStatus.OK
//        HttpStatus.NO_CONTENT
//        HttpStatus.BAD_REQUEST
//        HttpStatus.NOT_FOUND
//        HttpStatus.UNAUTHORIZED

@Slf4j
@RestControllerAdvice
@EnableWebMvc
public class GlobalRestControllerAdvice {

    // 쿼리 파라미터 추가안함
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String, String> noQueryParamHandle(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", "BAD_REQUEST");
        errorAttributes.put("message", "쿼리 파라미터 추가안함");
        errorAttributes.put("data", "");
        return errorAttributes;
    }
    // 네이버 액세스 토큰 잘못됨
    @ExceptionHandler(HttpClientErrorException.class)
    public Map<String, String> unauthHandler(HttpClientErrorException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", "UNAUTHORIZED");
        errorAttributes.put("message", "네이버 액세스 토큰 잘못됨");
        errorAttributes.put("data", "");
        return errorAttributes;
    }
    // 네이버 액세스 토큰 추가안함
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> nonavertokenHandle(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", "UNAUTHORIZED");
        errorAttributes.put("message", "네이버 액세스 토큰 추가안함");
        errorAttributes.put("data", "");
        return errorAttributes;
    }
    // JWT 없거나 잘못됨
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String, String> forbiddenHandle(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", "UNAUTHORIZED");
        errorAttributes.put("message", e.getMessage());
        errorAttributes.put("data", "");
        return errorAttributes;
    }

    // 조회 결과 없음
    @ExceptionHandler(NoResultException.class)
    public Map<String, String> noResultHandle(NoResultException e){
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", "NO_CONTENT");
        errorAttributes.put("message", e.getMessage());
        errorAttributes.put("data", "");
        return errorAttributes;
    }
//    // URI 잘못됨
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public Map<String, String> nfHandle(NoHandlerFoundException e) {
//        log.error(e.getMessage(), e);
//        Map<String, String> errorAttributes = new HashMap<>();
//        errorAttributes.put("status", "NOT_FOUND");
//        errorAttributes.put("message", "잘못된 요청입니다. URI를 확인해주세요.");
//        errorAttributes.put("data", "");
//        return errorAttributes;
//    }
}