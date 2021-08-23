package com.capstone.mountain.controller;

import com.capstone.mountain.response.Message;
import com.capstone.mountain.domain.Review;
import com.capstone.mountain.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<Message> saveReview(@RequestBody Map<String, String> req){
        Long userId = Long.parseLong(req.get("user"));
        Long courseId = Long.parseLong(req.get("course"));
        int score = Integer.parseInt(req.get("score"));
        Review review = reviewService.saveReview(userId, courseId, score);
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(review);
        return new ResponseEntity<>(message, OK);
    }
}
