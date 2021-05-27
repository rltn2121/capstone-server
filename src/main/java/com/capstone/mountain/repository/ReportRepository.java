package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.Record;
import com.capstone.mountain.domain.Report;
import com.capstone.mountain.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findByUserAndCourse(User user, Course course);
}
