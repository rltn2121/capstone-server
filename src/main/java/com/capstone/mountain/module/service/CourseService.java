package com.capstone.mountain.module.service;

import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.dto.CourseDetailDto;
import com.capstone.mountain.module.model.dto.CoursePreviewDto;
import com.capstone.mountain.module.model.dto.CourseMainPageDto;
import com.capstone.mountain.module.repository.CourseRepository;
import com.capstone.mountain.module.repository.FavoriteRepository;
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
    private final FavoriteRepository favoriteRepository;

    public Course findById(Long courseId){
        return courseRepository.findById(courseId).get();
    }
    public CourseDetailDto findCourseDetail(Long courseId, Long userId){
        CourseDetailDto courseDetail = courseRepository.findCourseDetail(courseId, userId);

//        boolean favoriteStatus = false;
//        if(favoriteRepository.getFavoriteStatus(userId, courseId) != null){
//
//        }
//        courseDetail.setLike_status(favoriteStatus);
        return courseDetail;
    }

    public Page<CoursePreviewDto> searchCourses(String keyword, String order, Pageable pageable){
        return courseRepository.searchCourses(keyword, order, pageable);
    }

    public List<CourseMainPageDto> getRecommendCoursesMain(Long userId){
        return courseRepository.getRecommendCourseMain(userId);
    }

    public List<CourseMainPageDto> getHotCourseMain(){
        return courseRepository.getHotCourseMain();
    }

    public Page<CoursePreviewDto> getRecommendCoursesDetail(Long userId, Pageable pageable){
        return courseRepository.getRecommendCourseDetail(userId, pageable);
    }
    public Page<CoursePreviewDto> getHotCoursesDetail(Pageable pageable){
        return courseRepository.getHotCourseDetail(pageable);
    }
}

