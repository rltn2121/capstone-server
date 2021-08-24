package com.capstone.mountain.module.model.dto;

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
    private int ele_dif;
    private String moving_time;
    private String difficulty;
    private String url;


//    private Long review_cnt;
//    private Double score;
    private String thumbnail;

    @QueryProjection
    public CoursePreviewDto(Long id, String name, Double distance, int ele_dif, String moving_time, String difficulty, String url, String thumbnail) {
        this.id = id;
        this.name = (name.length() > 20 ? name.substring(0, 17) + "..." : name);
        this.distance = distance;
        this.ele_dif = ele_dif;
        int hour = Integer.parseInt(moving_time.substring(0, 2));
        int minute = Integer.parseInt(moving_time.substring(3,5));

        this.moving_time = hour + "시간 " + minute + "분";
        this.difficulty = difficulty;
        this.url = url;
        this.thumbnail = thumbnail;
//        this.review_cnt = review_cnt;
//        this.score = (score == null ? 0 : Math.round(score*10)/10.0);
    }

}
