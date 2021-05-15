package com.capstone.mountain.repository;

import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.CoursePreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseRepositoryCustom {
    Page<CoursePreviewDto> searchCourses(String keyword, String cond, Pageable pageable );
    CourseDetailDto findCourseDetail(Long courseId);
}
