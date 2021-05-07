package com.capstone.mountain.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.jdo.annotations.Order;
import javax.naming.AuthenticationException;
import javax.servlet.ServletException;

import java.io.IOException;
import java.net.BindException;
import java.util.HashMap;
import java.util.Map;

//
//        HttpStatus.NOT_FOUND
//        HttpStatus.BAD_REQUEST
//        HttpStatus.OK
//        HttpStatus.CREATED
//        HttpStatus.UNAUTHORIZED
//        HttpStatus.FORBIDDEN
//        HttpStatus.INTERNAL_SERVER_ERROR
//        HttpStatus.METHOD_NOT_ALLOWED

@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

    // 쿼리 파라미터 추가안함
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String, String> noQueryParamHandle(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.toString());
        errorAttributes.put("message", "쿼리 파라미터 추가안함");
        errorAttributes.put("data", "");
        return errorAttributes;
    }
    // 네이버 액세스 토큰 잘못됨
    @ExceptionHandler(HttpClientErrorException.class)
    public Map<String, String> unauthHandler(HttpClientErrorException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.toString());
        errorAttributes.put("message", "네이버 액세스 토큰 잘못됨");
        errorAttributes.put("data", "");
        return errorAttributes;
    }
    // 네이버 액세스 토큰 추가안함
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> nonavertokenHandle(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.toString());
        errorAttributes.put("message", "네이버 액세스 토큰 추가안함");
        errorAttributes.put("data", "");
        return errorAttributes;
    }

    /**
     *
     *
     */


    /**
     * jwt 토큰 잘못됨
     */
    @ExceptionHandler(JWTDecodeException.class)
    public Map<String, String> jwt(JWTDecodeException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorAttributes.put("message", "jwt 토큰 잘못됨");
        errorAttributes.put("data", "");
        return errorAttributes;
    }


    /**
     * uri 잘못됨      (404)
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Map<String, String> nfHandle(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.NOT_FOUND.toString());
        errorAttributes.put("message", "404");
        errorAttributes.put("data", "");
        return errorAttributes;
    }

    /**
     * jwt 추가안함 또는 Bearer로 시작안함:    (403)
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String, String> forbiddenHandle(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.NOT_FOUND.toString());
        errorAttributes.put("message", "404");
        errorAttributes.put("data", "");
        return errorAttributes;
    }


    @ExceptionHandler(IOException.class)
    public Map<String, String> ioeHandle(IOException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.NOT_FOUND.toString());
        errorAttributes.put("message", "404");
        errorAttributes.put("data", "");
        return errorAttributes;
    }

    @ExceptionHandler(ServletException.class)
    public Map<String, String> servHandle(ServletException e) {
        log.error(e.getMessage(), e);
        Map<String, String> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.NOT_FOUND.toString());
        errorAttributes.put("message", "404");
        errorAttributes.put("data", "");
        return errorAttributes;
    }
}