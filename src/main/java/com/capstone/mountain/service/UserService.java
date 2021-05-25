package com.capstone.mountain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.UserProfileDto;
import com.capstone.mountain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public List<User> findByNickname(String nickname){
        return userRepository.findByNickname(nickname);
    }

    public Boolean updateProfile(Long userId, String nickname, int height, int weight){
        Long updated = userRepository.updateProfile(userId, nickname, height, weight);

        if(updated == 0)
            return false;
        else
            return true;
    }

    public UserProfileDto getUserProfile(Long userId){
        return userRepository.getUserProfile(userId);
    }

    public User join(User user){
        user.setRoles("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User chkUserExist(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null)
            return new User();
        return user;
    }


    public boolean checkValidUser(String jwtToken, Long user_id){
        String username =
                JWT.require(Algorithm.HMAC512("cos"))
                        .build()
                        .verify(jwtToken)
                        .getClaim("username")
                        .asString();
        User byUsername = userRepository.findByUsername(username);
        System.out.println("byUsername = " + byUsername.getId());
        System.out.println("user_id = " + user_id);
        if(byUsername.getId() == user_id){
            return true;
        }
        else
            return false;

    }

    public boolean isNicknameDuplicate(String nickname){
        List<User> byNickname = findByNickname(nickname);
        if(byNickname.size()>0){
            return true;
        }
        return false;
    }

    public User getUserFromJWT(String jwtToken) {
        String username =
                JWT.require(Algorithm.HMAC512("cos"))
                        .build()
                        .verify(jwtToken)       // 여기서 JWTDecodeException 발생
                        .getClaim("username")
                        .asString();
        User user = findByUsername(username);
        return user;
    }
}
