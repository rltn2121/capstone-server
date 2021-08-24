package com.capstone.mountain.module.controller;

import com.capstone.mountain.infra.exception.custom.NoResultException;
import com.capstone.mountain.infra.response.Message;
import com.capstone.mountain.module.model.dto.CoursePreviewDto;
import com.capstone.mountain.module.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final CourseService courseService;

    /**
     * 기능: 등산로 검색
     * @return 검색 결과 목록
     */
    @GetMapping
    public ResponseEntity<Message> searchCourses(@RequestParam String keyword,
                                                 @RequestParam String order,
                                                 Pageable pageable){
        Page<CoursePreviewDto> result = courseService.searchCourses(keyword, order, pageable);
        if(result.getNumberOfElements() == 0){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message(OK, "조회 성공", result.getContent());
        return new ResponseEntity<>(message, OK);
    }
}
