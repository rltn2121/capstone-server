package com.capstone.mountain.module.controller;

import com.capstone.mountain.infra.response.Message;
import com.capstone.mountain.module.model.domain.User;
import com.capstone.mountain.infra.exception.custom.NoResultException;
import com.capstone.mountain.module.model.dto.*;
import com.capstone.mountain.module.service.CourseService;
import com.capstone.mountain.module.service.MountainService;
import com.capstone.mountain.module.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private final MountainService mountainService;

    /**
     * 기능: 특정 등산로 상세 정보 조회
     * @return 특정 등산로 상세 정보
     */
    @GetMapping("/{course_id}")
    public ResponseEntity<Message> findCourseDetail(HttpServletRequest request,
                                                    @PathVariable Long course_id){
        User user = userService.getUserFromJWT(request);
        CourseDetailDto courseDetail = courseService.findCourseDetail(course_id, user.getId());
        if(courseDetail == null){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message(OK, "조회 성공", courseDetail);
        return new ResponseEntity<>(message, OK);
    }


    /**
     * 기능: 메인 화면 조회
     * @return 닉네임, 추천 등산로, 인기 등산로, 인기 산, 근처 산
     */
    @GetMapping("/main")
    public ResponseEntity<Message> getMainCourse(HttpServletRequest request,
                                                 @RequestParam double latitude,
                                                 @RequestParam double longitude){
        User user = userService.getUserFromJWT(request);

        // 메인에 표시할 목록 조회
        String nickname = user.getNickname();
        List<CourseMainPageDto> recommendCourse = courseService.getRecommendCoursesMain(user.getId());
        List<CourseMainPageDto> hotCourse = courseService.getHotCourseMain();
        List<MountainMainPageDto> hotMountain = mountainService.getHotMountain();
        List<MountainMainPageDto> nearMountain = mountainService.getNearMountain(latitude, longitude);

        // 1. 인기 등산로 없음 -> 예외 발생
        if(hotCourse.size() == 0){
            throw new NoResultException("조회 결과 없음.");
        }
        // 2. 추천 등산로 없음 -> 인기 등산로
        if(recommendCourse.size() == 0){
            recommendCourse = hotCourse;
        }
        MainDto mainDto = new MainDto(nickname, recommendCourse, hotCourse, hotMountain, nearMountain);
        Message message = new Message(OK, "조회 성공", mainDto);
        return new ResponseEntity<>(message, OK);
    }

    /**
     * 기능: 사용자 맞춤 등산로 목록 조회
     * @return 사용자 맞춤 등산로 목록
     */
    @GetMapping("/recommendation")
    public ResponseEntity<Message> getRecommendCourse(HttpServletRequest request,
                                                            Pageable pageable){
        User user = userService.getUserFromJWT(request);
        Page<CoursePreviewDto> result = courseService.getRecommendCoursesDetail(user.getId(), pageable);

        // 추천 목록이 없으면 인기있는 목록 반환
        if(result.getNumberOfElements()==0){
            result = courseService.getHotCoursesDetail(pageable);
            if(result.getNumberOfElements()==0){
                throw new NoResultException("조회 결과 없음.");
            }
        }
        Message message = new Message(OK, "조회 성공", result);
        return new ResponseEntity<>(message, OK);
    }

    /**
     * 기능: 인기 등산로 목록 조회
     * @return 인기 등산로 목록
     */
    @GetMapping("/hot")
    public ResponseEntity<Message> getHotCourse(Pageable pageable){
        Page<CoursePreviewDto> result = courseService.getHotCoursesDetail(pageable);
        if(result.getNumberOfElements()==0){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message(OK, "조회 성공", result);
        return new ResponseEntity<>(message, OK);
    }
}
