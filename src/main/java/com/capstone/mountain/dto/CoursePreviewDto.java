package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@ToString
public class CoursePreviewDto {
    private Long id;
    private String name;
    private Double distance;
    private int height;
    private String time;
    private String difficulty;
    private String url;

    private Long review_cnt;
    private Double score;
    private String thumbnail;

    @QueryProjection
    public CoursePreviewDto(Long id, String name, Double distance, int height, String time, String difficulty, String url, Long review_cnt, Double score, String thumbnail) {
        this.id = id;
        this.name = (name.length() > 20 ? name.substring(0, 17) + "..." : name);
        this.distance = distance;
        this.height = height;
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3,5));

        this.time = hour + "시간 " + minute + "분";
        this.difficulty = difficulty;
        this.url = url;
        this.review_cnt = review_cnt;
        this.score = (score == null ? 0 : Math.round(score*10)/10.0);
        this.thumbnail = thumbnail;
    }
}
