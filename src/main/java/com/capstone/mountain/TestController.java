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
import org.springframework.http.*;
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



    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }

    @GetMapping("/hello")
    public ResponseEntity<Message> hello(){
        //return new Hello("hello");
        Message message = new Message();
        message.setStatus(HttpStatus.OK);
        message.setMessage("전송 성공");
        message.setData(new Hello("hello"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @GetMapping("/jython")
    public void jythonTest(){
        JythonTest jythonTest = new JythonTest();
        jythonTest.test();
    }
}
