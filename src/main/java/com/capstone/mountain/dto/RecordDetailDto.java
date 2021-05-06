package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@ToString
public class RecordDetailDto {
    private String name;
    private String thumbnail;

    private LocalTime duration;
    private LocalTime movingTime;
    private LocalTime restTime;
    private Double distance;
    private Double speed;
    private int height;
    private Double calorie;
    private LocalDateTime date;

    @QueryProjection
    public RecordDetailDto(String name, String thumbnail, LocalTime duration, LocalTime movingTime, Double distance, Double speed, int height, Double calorie, LocalDateTime date) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.movingTime = movingTime;
        this.restTime = LocalTime.of(0,0,0);
        this.distance = distance;
        this.speed = speed;
        this.height = height;
        this.calorie = calorie;
        this.date = date;
    }
}
