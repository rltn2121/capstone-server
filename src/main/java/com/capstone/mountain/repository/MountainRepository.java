package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MountainRepository extends JpaRepository<Mountain, Long>, MountainRepositoryCustom {
    List<Mountain> findByName(String name);
}
