package com.capstone.mountain.module.service;

import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.domain.Review;
import com.capstone.mountain.module.model.domain.User;
import com.capstone.mountain.module.repository.CourseRepository;
import com.capstone.mountain.module.repository.ReviewRepository;
import com.capstone.mountain.module.repository.UserRepository;
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

    public Review saveReview(User user, Course course, int score){
        Review review = new Review(user, course, score);
        Review save = reviewRepository.save(review);
        return save;
    }
}
