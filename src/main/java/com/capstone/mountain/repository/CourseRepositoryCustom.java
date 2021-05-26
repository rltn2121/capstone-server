package com.capstone.mountain.repository;

import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.dto.CourseMainPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseRepositoryCustom {
    Page<CoursePreviewDto> searchCourses(String keyword, String cond, Pageable pageable );
    List<CourseMainPageDto> getRecommendCourseMain(Long userId);
    List<CourseMainPageDto> getHotCourseMain();
    Page<CoursePreviewDto> getRecommendCourseDetail(Long userId, Pageable pageable);
    Page<CoursePreviewDto> getHotCourseDetail(Pageable pageable);
    CourseDetailDto findCourseDetail(Long courseId);

}
