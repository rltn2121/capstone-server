package com.capstone.mountain.service;

import com.capstone.mountain.domain.Review;
import com.capstone.mountain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Optional<Review> findById(Long id){
        return reviewRepository.findById(id);
    }
}
