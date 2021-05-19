package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;

@Data
@NoArgsConstructor
@ToString
public class RecordDetailDto {
    private Long id;
    private String name;
    private String thumbnail;

    private String duration;
    private String movingTime;
    private String restTime;
    private Double distance;
    private Double speed;
    private int height;
    private int calorie;
    private String date;

    @QueryProjection
    public RecordDetailDto(Long id, String name, String thumbnail, String duration, String movingTime, Double distance, Double speed, int height, int calorie, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        //this.duration = duration.format(DateTimeFormatter.ofPattern("h시간 mm분 ss초"));
        //this.movingTime = movingTime.format(DateTimeFormatter.ofPattern("h시간 mm분 ss초"));
        //this.restTime = (duration.getHour() - movingTime.getHour()) + "시간 " + (duration.getMinute() - movingTime.getMinute()) + "분 " + (duration.getSecond() - movingTime.getSecond()) + "초";
        this.distance = distance;
        this.speed = speed;
        this.height = height;
        this.calorie = calorie;
        this.date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
