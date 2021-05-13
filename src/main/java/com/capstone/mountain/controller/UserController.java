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
import com.capstone.mountain.exception.custom.NoResultException;
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
    @PatchMapping("/user")
    public ResponseEntity<Message> updateProfile(HttpServletRequest request,
                                                 @RequestBody Map<String, String> req){
        Message message = new Message();

        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        User user = getUserFromJWT(jwtToken);
        String nickname = req.get("nickname");
        int height = Integer.parseInt(req.get("height"));
        int weight = Integer.parseInt(req.get("weight"));

        if(userService.isNicknameDuplicate(nickname)) {
            if(!nickname.equals(user.getNickname())){
                message.setStatus(BAD_REQUEST);
                message.setMessage("이미 존재하는 닉네임입니다.");
                return new ResponseEntity<>(message, message.getStatus());
            }
        }

        Boolean result = userService.updateProfile(user.getId(), nickname, height, weight);
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
    @GetMapping("/user")
    public ResponseEntity<Message> getUserProfile(HttpServletRequest request){
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        User user = getUserFromJWT(jwtToken);

        UserProfileDto userProfile = userService.getUserProfile(user.getId());
        if(userProfile == null){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setMessage("조회 성공");
        message.setStatus(OK);
        message.setData(userProfile);

        return new ResponseEntity<>(message, message.getStatus());
    }

    private User getUserFromJWT(String jwtToken) {
        String username =
                    JWT.require(Algorithm.HMAC512("cos"))
                            .build()
                            .verify(jwtToken)       // 여기서 JWTDecodeException 발생
                            .getClaim("username")
                            .asString();
        User user = userService.findByUsername(username);
        return user;
    }

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<Message> kakaoCallback(String code){
        
        System.out.println("나 코드 받았다~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("************ code: " + code + "*******************");
        // POST 방식으로 key=value 데이터를 요청 (카카오에게)
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        // rest api 키, redirect_uri 등은 직접 입력하지 않고, 변수로 저장해서 쓰는것이 더 좋음음
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "d3dec0f0ec318c6d7d147499579ed3cb");
        params.add("redirect_uri", "http://ec2-15-165-252-29.ap-northeast-2.compute.amazonaws.com/auth/kakao/callback");
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
        String access_token = oauthToken.getAccess_token();

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


            //response2.addHeader("Authorization", "Bearer " + jwtToken);
            message.setStatus(OK);
            message.setMessage("조회 성공");
            message.setData(new AccessTokenDto(jwtToken));
            return new ResponseEntity<>(message, OK);
        } catch(AuthenticationException e){
            e.printStackTrace();
        }
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
        return getJwt(response, username, password);

    }

    @PostMapping("/join")
    // username, nickname, password는 임의로 생성
    public ResponseEntity<Message> join(@RequestBody Map<String, String> req, HttpServletResponse response) {
        String username = req.get("username");
        String nickname = req.get("nickname");
        String password = "qwerty123";

        // username 존재하는지 확인
        User user = userService.findByUsername(username);

        // username이 없으면 회원가입 시키기
        if(user == null) {
            System.out.println("사용할 수 있는 username입니다.");
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setNickname(nickname);
            userService.join(user);
            System.out.println("회원가입 성공");
        }
        return autoLogin(response, password, user);
    }

    @PostMapping("/naver-login")
    public ResponseEntity<Message> naverLogin(@RequestBody Map<String, String> req, HttpServletResponse response) throws AuthenticationException {
        String access_token = req.get("access_token");
        System.out.println("access_token = " + access_token);
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
        return getJwt(response, username, password);
    }

    private ResponseEntity<Message> getJwt(HttpServletResponse response, String username, String password) {
        // 가입자 혹은 비가입자 체크 해서 처리
        User originUser = userService.chkUserExist(username);
        if (originUser.getUsername() == null) {
            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
            originUser.setUsername(username);
            originUser.setPassword(password);
            userService.join(originUser);
        }
        return autoLogin(response, password, originUser);
    }

    private ResponseEntity<Message> autoLogin(HttpServletResponse response, String password, User originUser) {
        System.out.println("자동 로그인을 진행합니다.");
        Message message = new Message();
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(originUser.getUsername(), password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 3. PrincipalDetails를 세션에 담음 (권한 관리를 위해서)
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            String jwtToken = JWT.create()
                    .withSubject(principalDetails.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 100000)))
                    .withClaim("id", principalDetails.getUser().getId())
                    .withClaim("username", principalDetails.getUser().getUsername())
                    .sign(Algorithm.HMAC512("cos"));
            response.addHeader("Authorization", "Bearer " + jwtToken);
            message.setStatus(OK);
            message.setMessage("로그인 성공");
            message.setData(new AccessTokenDto(jwtToken));
            return new ResponseEntity<>(message, OK);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
