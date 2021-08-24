package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>, FavoriteRepositoryCustom {

}
