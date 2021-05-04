package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@ToString
public class CourseDetailDto {
    private Long id;
    private String name;
    private String location;
    private Double distance;
    private LocalTime time;
    private Double speed;
    private Double height;
    private int difficulty;
    private String url;
    private Long review_cnt;
    private Double score;

    @QueryProjection
    public CourseDetailDto(Long id, String name, String location, Double distance, LocalTime time, Double speed, Double height, int difficulty, String url, Long review_cnt, Double score) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.distance = distance;
        this.time = time;
        this.speed = speed;
        this.height = height;
        this.difficulty = difficulty;
        this.url = url;
        this.review_cnt = review_cnt;
        this.score = (score == null ? 0 : score);
    }
}
