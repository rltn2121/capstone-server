package com.capstone.mountain.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.capstone.mountain.Message;
import com.capstone.mountain.auth.KakaoProfile;
import com.capstone.mountain.auth.NaverProfile;
import com.capstone.mountain.auth.OAuthToken;
import com.capstone.mountain.auth.PrincipalDetails;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.AccessTokenDto;
import com.capstone.mountain.dto.UserProfileDto;
import com.capstone.mountain.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequiredArgsConstructor
public class UserController{
    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     *
     * 기능: 사용자 프로필 수정
     * @return 성공 여부 메시지
     */
    @PatchMapping("/user/{user_id}")
    public ResponseEntity<Message> updateProfile(HttpServletRequest request,
                                                @PathVariable Long user_id,
                                                 @RequestBody Map<String, String> req){
        Message message = new Message();

        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        String nickname = req.get("nickname");
        int height = Integer.parseInt(req.get("height"));
        int weight = Integer.parseInt(req.get("weight"));
        
        boolean isValid = userService.checkValidUser(jwtToken, user_id);
        if(!isValid){
            message.setStatus(BAD_REQUEST);
            message.setMessage("다른 사용자의 프로필을 변경할 수 없습니다.");
            return new ResponseEntity<>(message, message.getStatus());
        }
        
        List<User> byNickname = userService.findByNickname(nickname);
        if(byNickname.size()>0){
            message.setStatus(BAD_REQUEST);
            message.setMessage("이미 존재하는 닉네임입니다.");
            return new ResponseEntity<>(message, message.getStatus());
        }

        Boolean result = userService.updateProfile(user_id, nickname, height, weight);
        if(result){
            message.setStatus(CREATED);
            message.setMessage("프로필을 성공적으로 변경했습니다.");
        }
        else{
            message.setStatus(BAD_REQUEST);
            message.setMessage("프로필 변경 중 오류가 발생했습니다.");
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    /**
     * 기능: 사용자 프로필 조회
     * @return 사용자 프로필
     */
    @GetMapping("/user/{user_id}")
    public ResponseEntity<Message> getUsserProfile(@PathVariable("user_id") Long id){
        Message message = new Message();

        UserProfileDto userProfile = userService.getUserProfile(id);
        if(userProfile == null){
            message.setMessage("존재하지 않는 사용자입니다.");
            message.setStatus(BAD_REQUEST);
        }
        else{
            message.setMessage("조회 성공");
            message.setStatus(OK);
            message.setData(userProfile);
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody Authentication kakaoCallback(String code){
        // POST 방식으로 key=value 데이터를 요청 (카카오에게)
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        // rest api 키, redirect_uri 등은 직접 입력하지 않고, 변수로 저장해서 쓰는것이 더 좋음음
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "7698fd9c957f6e2ebe9d5d8e97cea593");
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

        System.out.println("******************************************");
        System.out.println("액세스 토큰 정보 응답 확인");
        System.out.println("response = " + response);
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("카카오 액세스 토큰: " + oauthToken.getAccess_token());
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
        System.out.println(response2.getBody());

        System.out.println("***********************************");

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // User 오브젝트 : username, password, email
        System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
        System.out.println("카카오 이메일 : "+kakaoProfile.getKakao_account().getEmail());

        System.out.println("블로그서버 유저네임 : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("블로그서버 이메일 : "+kakaoProfile.getKakao_account().getEmail());
        // UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
        UUID garbagePassword = UUID.randomUUID();
        System.out.println("블로그서버 패스워드 : "+ garbagePassword);
        return null;
    }

    @PostMapping("/kakao-login")
    public ResponseEntity<Message> kakaoLogin(@RequestBody Map<String, String> req, HttpServletResponse response) throws AuthenticationException {
        String access_token = req.get("access_token");
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+access_token);
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
        System.out.println(response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String username = kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId();
        String password = "qwerty123";

        // 가입자 혹은 비가입자 체크 해서 처리
        User originUser = userService.chkUserExist(username);
        if(originUser.getUsername() == null) {
            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
            originUser.setUsername(username);
            originUser.setPassword(password);
            userService.join(originUser);
        }

        System.out.println("자동 로그인을 진행합니다.");
        Message message = new Message();
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(originUser.getUsername(), password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 3. PrincipalDetails를 세션에 담음 (권한 관리를 위해서)
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            String jwtToken = JWT.create()
                    .withSubject(principalDetails.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 120)))
                    .withClaim("id", principalDetails.getUser().getId())
                    .withClaim("username", principalDetails.getUser().getUsername())
                    .sign(Algorithm.HMAC512("cos"));
            response.addHeader("Authorization", "Bearer " + jwtToken);
            message.setStatus(OK);
            message.setMessage("조회 성공");
            message.setData(new AccessTokenDto(jwtToken));
            return new ResponseEntity<>(message, OK);
        } catch(AuthenticationException e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/google-login")
    public @ResponseBody String googleLogin(@RequestBody Map<String, String> req, HttpServletResponse response) throws AuthenticationException {
        String access_token = req.get("access_token");
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+access_token);
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://www.googleapis.com/drive/v2/files",
                HttpMethod.POST,
                naverProfileRequest2,
                String.class
        );
        System.out.println(response2.getBody());

//        ObjectMapper objectMapper2 = new ObjectMapper();
//        NaverProfile kakaoProfile = null;
//        try {
//            kakaoProfile = objectMapper2.readValue(response2.getBody(), NaverProfile.class);
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//
//        String username = kakaoProfile.getResponse().getEmail()+"_"+kakaoProfile.getResponse().getId();
//        String password = "qwerty123";
//
//        // 가입자 혹은 비가입자 체크 해서 처리
//        User originUser = userService.chkUserExist(username);
//        if(originUser.getUsername() == null) {
//            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
//            originUser.setUsername(username);
//            originUser.setPassword(password);
//            userService.join(originUser);
//        }
//
//        System.out.println("자동 로그인을 진행합니다.");
//        try{
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(originUser.getUsername(), password);
//            Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//            // 3. PrincipalDetails를 세션에 담음 (권한 관리를 위해서)
//            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//
//            String jwtToken = JWT.create()
//                    .withSubject(principalDetails.getUsername())
//                    .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 120)))
//                    .withClaim("id", principalDetails.getUser().getId())
//                    .withClaim("username", principalDetails.getUser().getUsername())
//                    .sign(Algorithm.HMAC512("cos"));
//            response.addHeader("Authorization", "Bearer " + jwtToken);
//            return jwtToken;
//        } catch(AuthenticationException e){
//            e.printStackTrace();
//        }
        return null;
    }

    @PostMapping("/naver-login")
    public ResponseEntity<Message> naverLogin(@RequestBody Map<String, String> req, HttpServletResponse response) throws AuthenticationException {
        System.out.println("req.get 이전");
        String access_token = req.get("access_token");
        System.out.println("req.get 이후");
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+access_token);
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                naverProfileRequest2,
                String.class
        );
        System.out.println(response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        NaverProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), NaverProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        String username = kakaoProfile.getResponse().getEmail()+"_"+kakaoProfile.getResponse().getId();
        String password = "qwerty123";

        // 가입자 혹은 비가입자 체크 해서 처리
        User originUser = userService.chkUserExist(username);
        if(originUser.getUsername() == null) {
            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
            originUser.setUsername(username);
            originUser.setPassword(password);
            userService.join(originUser);
        }

        System.out.println("자동 로그인을 진행합니다.");
        Message message = new Message();
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(originUser.getUsername(), password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 3. PrincipalDetails를 세션에 담음 (권한 관리를 위해서)
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            String jwtToken = JWT.create()
                    .withSubject(principalDetails.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 120)))
                    .withClaim("id", principalDetails.getUser().getId())
                    .withClaim("username", principalDetails.getUser().getUsername())
                    .sign(Algorithm.HMAC512("cos"));
            response.addHeader("Authorization", "Bearer " + jwtToken);



            message.setStatus(OK);
            message.setMessage("조회 성공");
            message.setData(new AccessTokenDto(jwtToken));
            return new ResponseEntity<>(message, OK);
        } catch(AuthenticationException e){
            e.printStackTrace();
        }

        return null;
    }
}
