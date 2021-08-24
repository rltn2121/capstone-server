package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {
}
