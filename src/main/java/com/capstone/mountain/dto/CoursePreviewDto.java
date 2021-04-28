package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class CoursePreviewDto {
    private Long id;
    private String name;
    private Double distance;
    private Double height;
    private LocalDateTime time;
    private int difficulty;
    private String url;
    private Long reviewCnt;
    private Double score;

    @QueryProjection
    public CoursePreviewDto(Long id, String name, Double distance, Double height, LocalDateTime time, int difficulty, String url, Long reviewCnt, Double score) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.height = height;
        this.time = time;
        this.difficulty = difficulty;
        this.url = url;
        this.reviewCnt = reviewCnt;
        this.score = score;
    }
}
