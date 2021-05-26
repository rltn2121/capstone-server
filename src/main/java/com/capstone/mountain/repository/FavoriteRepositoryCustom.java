package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.Favorite;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.CoursePreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteRepositoryCustom {
    Page<CoursePreviewDto> findByUser(User user, Pageable pageable);
    Favorite isFavoriteExist(User user, Course course);
    long toggleFavorite(User user, Course course, boolean status);
}
