package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.domain.Review;
import com.capstone.mountain.module.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser(User user);
    List<Review> findByCourse(Course course);
}
