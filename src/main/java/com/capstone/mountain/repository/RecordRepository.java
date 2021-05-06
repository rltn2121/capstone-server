package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Record;
import com.capstone.mountain.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordRepositoryCustom{
}
