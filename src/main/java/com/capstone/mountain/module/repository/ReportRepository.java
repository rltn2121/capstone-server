package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.domain.Report;
import com.capstone.mountain.module.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findByUserAndCourse(User user, Course course);
}
