package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@ToString
public class CourseDetailDto {
    private Long id;
    private String name;
    private String location;
    private Double distance;
    private String moving_time_str;
    private String total_time_str;
    private Double avg_speed;
    private Double avg_pace;
    private int max_height;
    private int min_height;
    private int ele_dif;
    private int total_uphill;
    private int total_downhill;

    private String difficulty;
    private String thumbnail;
    private String url;
    private String date;
   // private Long review_cnt;
  //  private Double score;

    @QueryProjection
    public CourseDetailDto(Long id, String name, String location, Double distance, String moving_time_str, String total_time_str, Double avg_speed, Double avg_pace, int max_height, int min_height, int ele_dif, int total_uphill, int total_downhill, String difficulty, String thumbnail, String url, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.distance = distance;
        this.moving_time_str = moving_time_str;
        this.total_time_str = total_time_str;
        this.avg_speed = avg_speed;
        this.avg_pace = avg_pace;

        int hour = Integer.parseInt(moving_time_str.substring(0, 2));
        int minute = Integer.parseInt(moving_time_str.substring(3,5));
        int second = Integer.parseInt(moving_time_str.substring(6,8));
        this.moving_time_str = hour + "시간 " + minute + "분 " + second + "초";

        hour = Integer.parseInt(total_time_str.substring(0, 2));
        minute = Integer.parseInt(total_time_str.substring(3,5));
        second = Integer.parseInt(total_time_str.substring(6,8));
        this.total_time_str = hour + "시간 " + minute + "분 " + second + "초";

        this.max_height = max_height;
        this.min_height = min_height;
        this.ele_dif = ele_dif;
        this.total_uphill = total_uphill;
        this.total_downhill = total_downhill;
        this.difficulty = difficulty;
        this.thumbnail = thumbnail;
        this.url = url;
        this.date = date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth() + " " +
        date.getHour() + ":" + date.getMinute() + ":" + date.getSecond();
//        this.score = (score == null ? 0 : Math.round(score*10)/10.0);
    }



}
