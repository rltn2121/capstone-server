package com.capstone.mountain.module.service;

import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.domain.Favorite;
import com.capstone.mountain.module.model.domain.User;
import com.capstone.mountain.module.model.dto.CoursePreviewDto;
import com.capstone.mountain.infra.exception.custom.NoResultException;
import com.capstone.mountain.module.repository.CourseRepository;
import com.capstone.mountain.module.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
