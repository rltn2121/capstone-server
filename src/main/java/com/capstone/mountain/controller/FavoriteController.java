package com.capstone.mountain.controller;

import com.capstone.mountain.Message;
import com.capstone.mountain.domain.Favorite;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.dto.RecordPreviewDto;
import com.capstone.mountain.exception.custom.NoResultException;
import com.capstone.mountain.service.FavoriteService;
import com.capstone.mountain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UserService userService;


    @GetMapping("/favorite")
    public ResponseEntity<Message> findRecords(HttpServletRequest request, Pageable pageable){
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        User user = userService.getUserFromJWT(jwtToken);
        Page<CoursePreviewDto> favoriteList = favoriteService.getFavoriteList(user, pageable);
        if(favoriteList.getNumberOfElements() == 0){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(favoriteList);
        return new ResponseEntity<>(message, OK);
    }

    @PostMapping("/favorite")
    public Map<String, String> toggleFavorite(HttpServletRequest request,@RequestBody Map<String, String> req) {

        Long courseId = Long.parseLong(req.get("course_id"));
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        User user = userService.getUserFromJWT(jwtToken);

        String result = favoriteService.toggleFavorite(user, courseId);
        Map<String, String> message = new HashMap<>();
        if(result.equals("추가 성공"))
            message.put("status", "ADDED");
        else if(result.equals("제거 성공"))
            message.put("status", "REMOVED");
        message.put("message", result);
        return message;
    }
}
