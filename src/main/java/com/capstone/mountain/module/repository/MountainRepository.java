package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.domain.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MountainRepository extends JpaRepository<Mountain, Long>, MountainRepositoryCustom {
    List<Mountain> findByName(String name);
}
