package com.capstone.mountain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.capstone.mountain.auth.KakaoProfile;
import com.capstone.mountain.auth.PrincipalDetails;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.repository.UserRepository;
import com.capstone.mountain.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    @PostMapping("token")
    public String token() {
        return "<h1>token</h1>";
    }

    @PostMapping("/join")
    public String join(@RequestBody User user) {
        userService.join(user);
        return "회원가입완료";
    }

    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }

    @GetMapping("/kakao-login")
    public @ResponseBody String kakaoLogin(@RequestBody Map<String, String> req, HttpServletResponse response) throws AuthenticationException {
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
       // UUID garbagePassword = UUID.randomUUID();
       // System.out.println("블로그서버 패스워드 : "+ garbagePassword);

        String username = kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId();
        String password = "qwerty123";

        User originUser = userService.chkUserExist(username);
        // 가입자 혹은 비가입자 체크 해서 처리

        if(originUser.getUsername() == null) {
            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
            originUser.setUsername(username);
            originUser.setPassword(password);
            userService.join(originUser);
        }

        System.out.println("자동 로그인을 진행합니다.");
        try{
            System.out.println("password = " + password);
            System.out.println("originUser.getPassword() = " + originUser.getPassword());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(originUser.getUsername(), password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            System.out.println("authenticationManager.authenticate 완료");

            // 3. PrincipalDetails를 세션에 담음 (권한 관리를 위해서)
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            System.out.println("로그인이 되었다는 뜻 =>" + principalDetails.getUser().getUsername());

            String jwtToken = JWT.create()
                    .withSubject(principalDetails.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 120)))
                    .withClaim("id", principalDetails.getUser().getId())
                    .withClaim("username", principalDetails.getUser().getUsername())
                    .sign(Algorithm.HMAC512("cos"));
            response.addHeader("Authorization", "Bearer " + jwtToken);
            return jwtToken;
        } catch(AuthenticationException e){
            e.printStackTrace();
        }

        return null;
    }
}
