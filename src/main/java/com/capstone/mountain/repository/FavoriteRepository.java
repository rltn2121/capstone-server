package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Favorite;
import com.capstone.mountain.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>, FavoriteRepositoryCustom {

}
