package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.Review;
import com.capstone.mountain.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser(User user);
    List<Review> findByCourse(Course course);
}
