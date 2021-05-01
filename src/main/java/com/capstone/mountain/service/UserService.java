package com.capstone.mountain.service;

import com.capstone.mountain.domain.User;
import com.capstone.mountain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public String updateProfile(Long userId, String nickname, int height, int weight){
        Long updated = userRepository.updateProfile(userId, nickname, height, weight);

        if(updated == 0)
            return "프로필 수정 중 오류가 발생했습니다.";
        else
            return "프로필 수정이 완료되었습니다.";
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

}
