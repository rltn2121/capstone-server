package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordRepositoryCustom{
}
