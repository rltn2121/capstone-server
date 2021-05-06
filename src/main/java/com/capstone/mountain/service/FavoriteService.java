package com.capstone.mountain.service;

import com.capstone.mountain.domain.Favorite;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.repository.FavoriteRepository;
import com.capstone.mountain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    public List<Favorite> getFavoriteList(Long userId){
        User user = userRepository.findById(userId).get();
        List<Favorite> byUser = favoriteRepository.findByUser(user);
        return byUser;
    }
}
