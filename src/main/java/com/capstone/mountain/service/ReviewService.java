package com.capstone.mountain.service;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.Review;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.repository.CourseRepository;
import com.capstone.mountain.repository.ReviewRepository;
import com.capstone.mountain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public Optional<Review> findById(Long id){
        return reviewRepository.findById(id);
    }



    public Review saveReview(Long userId, Long courseId, int score){
        User user = userRepository.findById(userId).get();
        Course course = courseRepository.findById(courseId).get();
        Review review = new Review(user, course, score);
        Review save = reviewRepository.save(review);
        return save;
    }
}
