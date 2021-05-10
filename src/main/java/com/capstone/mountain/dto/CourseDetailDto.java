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
    private int difficulty;
    private String url;
    private Long review_cnt;
    private Double score;
    private String thumbnail;

    @QueryProjection
    public CourseDetailDto(Long id, String name, String location, Double distance, LocalTime time, Double speed, int height, int difficulty, String url, Long review_cnt, Double score, String thumbnail) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.distance = distance;
        this.time = (time == null) ? "0시간 0분 0초" : time.getHour() + "시간 " + time.getMinute() + "분 " + time.getSecond() + "초";
        this.speed = speed;
        this.height = height;
        this.difficulty = difficulty;
        this.url = url;
        this.review_cnt = review_cnt;
        this.score = (score == null ? 0 : Math.round(score*10)/10.0);
        this.thumbnail = thumbnail;
    }
}
