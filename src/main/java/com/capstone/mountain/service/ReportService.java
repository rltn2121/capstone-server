package com.capstone.mountain.service;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.Report;
import com.capstone.mountain.domain.Review;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.repository.CourseRepository;
import com.capstone.mountain.repository.ReportRepository;
import com.capstone.mountain.repository.ReviewRepository;
import com.capstone.mountain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public Report findReport(User user, Course course){
        return reportRepository.findByUserAndCourse(user, course);
    }

    public Report saveReport(User user, Course course, String reason){
        Report report = new Report(user, course, reason);
        Report save = reportRepository.save(report);
        return save;
    }
}
