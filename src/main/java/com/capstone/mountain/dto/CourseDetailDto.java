package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@ToString
public class CourseDetailDto {
    private Long id;
    private String name;
    private String location;
    private Double distance;
    private String time;
    private Double speed;
    private int height;
    private String difficulty;
    private String url;
    private Long review_cnt;
    private Double score;
    private String thumbnail;

    @QueryProjection
    public CourseDetailDto(Long id, String name, String location, Double distance, String time, Double speed, int height, String difficulty, String url, Long review_cnt, Double score, String thumbnail) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.distance = distance;
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3,5));
        this.time = hour + "시간 " + minute + "분";
        this.speed = speed;
        this.height = height;
        this.difficulty = difficulty;
        this.url = url;
        this.review_cnt = review_cnt;
        this.score = (score == null ? 0 : Math.round(score*10)/10.0);
        this.thumbnail = thumbnail;
    }
}
