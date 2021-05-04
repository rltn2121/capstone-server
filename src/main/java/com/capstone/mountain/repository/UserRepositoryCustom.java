package com.capstone.mountain.repository;

import com.capstone.mountain.dto.UserProfileDto;

public interface UserRepositoryCustom {
    public Long updateProfile(Long userId, String nickname, int height, int weight);
    public UserProfileDto getUserProfile(Long userId);
}
