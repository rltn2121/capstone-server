package com.capstone.mountain.controller;

import com.capstone.mountain.Message;
import com.capstone.mountain.domain.Favorite;
import com.capstone.mountain.dto.RecordPreviewDto;
import com.capstone.mountain.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;


    @GetMapping("/favorite")
    public ResponseEntity<Message> findRecords(@RequestParam("user") Long id){
        List<Favorite> favoriteList = favoriteService.getFavoriteList(id);
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(favoriteList);
        return new ResponseEntity<>(message, OK);
    }
}
