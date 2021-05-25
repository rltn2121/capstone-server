package com.capstone.mountain.controller;

import com.capstone.mountain.Hello;
import com.capstone.mountain.Message;
import com.capstone.mountain.domain.Mountain;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.dto.CourseRecommendDto;
import com.capstone.mountain.exception.custom.NoResultException;
import com.capstone.mountain.service.CourseService;
import com.capstone.mountain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/recommendation/main")
    public ResponseEntity<Message> getRecommendCourseMain(HttpServletRequest request){
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        User user = userService.getUserFromJWT(jwtToken);
        List<CourseRecommendDto> recommendCourses = courseService.getRecommendCoursesMain(user.getId());
        if(recommendCourses.size()==0){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(recommendCourses);
        return new ResponseEntity<>(message, OK);
    }

    @GetMapping("/recommendation/detail")
    public ResponseEntity<Message> getRecommendCourseDetail(HttpServletRequest request,
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
}
