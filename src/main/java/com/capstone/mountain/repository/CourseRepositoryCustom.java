package com.capstone.mountain.repository;

import com.capstone.mountain.dto.CoursePreviewDto;

import java.util.List;

public interface CourseRepositoryCustom {
    List<CoursePreviewDto> searchCourses(String keyword, String cond);
}
