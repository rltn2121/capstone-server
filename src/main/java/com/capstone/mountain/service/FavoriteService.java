package com.capstone.mountain.service;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.Favorite;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.exception.custom.NoResultException;
import com.capstone.mountain.repository.CourseRepository;
import com.capstone.mountain.repository.FavoriteRepository;
import com.capstone.mountain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final CourseRepository courseRepository;
    public Page<CoursePreviewDto> getFavoriteList(User user, Pageable pageable){
        return favoriteRepository.findByUser(user, pageable);
    }

    public String toggleFavorite(User user, Long courseId){
        String message = "";
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NoResultException("등산로 id 존재하지 않음"));

        Favorite favoriteExist = favoriteRepository.isFavoriteExist(user, course);
        // 목록에 없음
        if(favoriteExist == null){
            Favorite favorite = new Favorite(user, course, true);
            Favorite save = favoriteRepository.save(favorite);
            if(save == null){
                message = "실패";
            }
            else{
                message = "추가 성공";
            }
        }
        // 목록에 있음
        else{
            boolean status = favoriteExist.isStatus();
            long result = favoriteRepository.toggleFavorite(user, course, !status);
            if(result == 0) {
                message = "실패";
            }
            else{
                if(!status == true){
                    message = "추가 성공";
                }
                else{
                    message = "제거 성공";
                }
            }
        }
        return message;
    }
}
