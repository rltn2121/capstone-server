package com.capstone.mountain.service;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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
}
