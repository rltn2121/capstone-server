package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@ToString
public class CoursePreviewDto {
    private Long id;
    private String name;
    private Double distance;
    private Double height;
    private LocalTime time;
    private int difficulty;
    private String url;
    private Long review_cnt;
    private Double score;
    private String thumbnail;

    @QueryProjection
    public CoursePreviewDto(Long id, String name, Double distance, Double height, LocalTime time, int difficulty, String url, Long review_cnt, Double score, String thumbnail) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.height = height;
        this.time = time;
        this.difficulty = difficulty;
        this.url = url;
        this.review_cnt = review_cnt;
        this.score = (score == null ? 0 : score);
        this.thumbnail = thumbnail;
    }
}
