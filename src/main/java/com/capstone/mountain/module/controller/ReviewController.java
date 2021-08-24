package com.capstone.mountain.module.controller;

import com.capstone.mountain.infra.response.Message;
import com.capstone.mountain.infra.response.NoDataMessage;
import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.domain.Review;
import com.capstone.mountain.module.model.domain.User;
import com.capstone.mountain.module.model.dto.ReviewSaveDto;
import com.capstone.mountain.module.service.CourseService;
import com.capstone.mountain.module.service.ReviewService;
import com.capstone.mountain.module.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final CourseService courseService;

    /**
     * 기능: 리뷰 등록하기
     * @return 없음
     */
    @PostMapping
    public ResponseEntity<NoDataMessage> saveReview(HttpServletRequest request, @RequestBody ReviewSaveDto reviewSaveDto){
        User user = userService.getUserFromJWT(request);
        Course course = courseService.findById(reviewSaveDto.getCourse());
        int score = reviewSaveDto.getScore();

        reviewService.saveReview(user, course, score);
        NoDataMessage message = new NoDataMessage("OK", "조회 성공");
        return new ResponseEntity<>(message, OK);
    }
}
