package com.capstone.mountain.controller;

import com.capstone.mountain.domain.User;
import com.capstone.mountain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{user_id}")
    public Optional<User> findById(@PathVariable("user_id") Long id){
        return userService.findById(id);
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code){

        // POST 방식으로 key=value 데이터를 요청 (카카오에게)
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        // rest api 키, redirect_uri 등은 직접 입력하지 않고, 변수로 저장해서 쓰는것이 더 좋음음
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "0424bf7b6fda69a5ef3c591a8d4f0f43");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청하기, 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",  // 요청할 주소
                HttpMethod.POST,                            // 타입
                kakaoTokenRequest,                          // 파라미터, 헤더
                String.class                                // 응답의 타입
        );

        System.out.println("response = " + response.toString());
        return "카카오 토큰 요청 완료\n " + response;
    }
}
