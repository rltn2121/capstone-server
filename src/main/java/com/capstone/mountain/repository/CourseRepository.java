package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
