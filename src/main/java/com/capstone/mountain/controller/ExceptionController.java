package com.capstone.mountain.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public void entrypointException() {
        throw new AccessDeniedException("JWT 토큰을 확인해주세요.");
    }
}
