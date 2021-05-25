package com.capstone.mountain.service;

import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.dto.CourseRecommendDto;
import com.capstone.mountain.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseDetailDto findCourseDetail(Long courseId){
        return courseRepository.findCourseDetail(courseId);
    }

    public Page<CoursePreviewDto> searchCourses(String keyword, String order, Pageable pageable){
        return courseRepository.searchCourses(keyword, order, pageable);
    }

    public List<CourseRecommendDto> getRecommendCoursesMain(Long userId){
        return courseRepository.getRecommendCourseMain(userId);
    }

    public Page<CoursePreviewDto> getRecommendCoursesDetail(Long userId, Pageable pageable){
        return courseRepository.getRecommendCourseDetail(userId, pageable);
    }
}

