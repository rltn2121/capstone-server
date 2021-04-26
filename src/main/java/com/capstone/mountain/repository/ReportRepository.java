package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
