package com.capstone.mountain.controller;

import com.capstone.mountain.Message;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.CourseMainPageDto;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.dto.MainDto;
import com.capstone.mountain.exception.custom.NoResultException;
import com.capstone.mountain.service.CourseService;
import com.capstone.mountain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;

    @GetMapping("/course/{course_id}")
    public ResponseEntity<Message> findCourseDetail(@PathVariable("course_id") Long id){
        CourseDetailDto courseDetail = courseService.findCourseDetail(id);
        if(courseDetail == null){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(courseDetail);
        return new ResponseEntity<>(message, OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Message>  searchCourses(@RequestParam String keyword,
                                                  @RequestParam String order,
                                                  Pageable pageable){
        Page<CoursePreviewDto> result = courseService.searchCourses(keyword, order, pageable);
        if(result.getNumberOfElements() == 0){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(result.getContent());
        return new ResponseEntity<>(message, OK);
    }

    @GetMapping("/course/main")
    public ResponseEntity<Message> getMainCourse(HttpServletRequest request){
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        User user = userService.getUserFromJWT(jwtToken);

        List<CourseMainPageDto> recommendCourse = courseService.getRecommendCoursesMain(user.getId());

        // 최근 12개월 이내 따라간 횟수 상위 20개
        List<CourseMainPageDto> hotCourse = courseService.getHotCourseMain();

        MainDto mainDto = new MainDto(recommendCourse, hotCourse);

        // 1. 둘 다 없음
        if(recommendCourse.size()==0 && hotCourse.size() == 0){
            throw new NoResultException("조회 결과 없음.");
        }

        // 2. 둘 중 하나 없음

        // 3. 둘 다 있음
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(mainDto);
        return new ResponseEntity<>(message, OK);
    }

    @GetMapping("/course/recommendation")
    public ResponseEntity<Message> getRecommendCourse(HttpServletRequest request,
                                                            Pageable pageable){
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        User user = userService.getUserFromJWT(jwtToken);
        Page<CoursePreviewDto> result = courseService.getRecommendCoursesDetail(user.getId(), pageable);
        if(result.getNumberOfElements()==0){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(result);
        return new ResponseEntity<>(message, OK);
    }
    @GetMapping("/course/hot")
    public ResponseEntity<Message> getHotCourse(Pageable pageable){
        Page<CoursePreviewDto> result = courseService.getHotCoursesDetail(pageable);
        if(result.getNumberOfElements()==0){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(result);
        return new ResponseEntity<>(message, OK);
    }
}
