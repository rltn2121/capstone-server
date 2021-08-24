package com.capstone.mountain.module.service;

import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.domain.Report;
import com.capstone.mountain.module.model.domain.User;
import com.capstone.mountain.module.repository.CourseRepository;
import com.capstone.mountain.module.repository.ReportRepository;
import com.capstone.mountain.module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
