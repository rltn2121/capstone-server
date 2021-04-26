package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
