package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.domain.Favorite;
import com.capstone.mountain.module.model.domain.User;
import com.capstone.mountain.module.model.dto.CoursePreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteRepositoryCustom {
    Page<CoursePreviewDto> findByUser(User user, Pageable pageable);
    Favorite isFavoriteExist(User user, Course course);
    long toggleFavorite(User user, Course course, boolean status);
    boolean getFavoriteStatus(Long userId, Long courseId);
}
