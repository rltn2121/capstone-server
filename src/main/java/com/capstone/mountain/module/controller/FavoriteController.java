package com.capstone.mountain.module.controller;

import com.capstone.mountain.infra.response.Message;
import com.capstone.mountain.infra.response.NoDataMessage;
import com.capstone.mountain.module.model.domain.User;
import com.capstone.mountain.module.model.dto.CoursePreviewDto;
import com.capstone.mountain.infra.exception.custom.NoResultException;
import com.capstone.mountain.module.service.FavoriteService;
import com.capstone.mountain.module.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UserService userService;

    /**
     * 기능: 찜 목록 조회
     * @return 찜 목록
     */
    @GetMapping
    public ResponseEntity<Message> getFavoriteList(HttpServletRequest request, Pageable pageable){

        User user = userService.getUserFromJWT(request);

        Page<CoursePreviewDto> favoriteList = favoriteService.getFavoriteList(user, pageable);
        if(favoriteList.getNumberOfElements() == 0){
            throw new NoResultException("조회 결과 없음.");
        }

        Message message = new Message(OK, "조회 성공", favoriteList);
        return new ResponseEntity<>(message, OK);
    }

    /**
     * 기능: 찜 목록 추가/제거 (토글)
     * @return 없음
     */
    @PostMapping
    public ResponseEntity<NoDataMessage> toggleFavorite(HttpServletRequest request, @RequestBody Map<String, String> req) {
        Long courseId = Long.parseLong(req.get("course_id"));
        User user = userService.getUserFromJWT(request);
        String result = favoriteService.toggleFavorite(user, courseId);

        NoDataMessage message = null;
        if(result.equals("추가 성공"))
            message = new NoDataMessage("ADDED", result);
        else if(result.equals("제거 성공"))
            message = new NoDataMessage("REMOVED", result);
        return new ResponseEntity<>(message, OK);
    }
}
