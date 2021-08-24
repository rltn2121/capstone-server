package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.dto.UserProfileDto;

public interface UserRepositoryCustom {
    public Long updateProfile(Long userId, String nickname, int height, int weight);
    public UserProfileDto getUserProfile(Long userId);
}
