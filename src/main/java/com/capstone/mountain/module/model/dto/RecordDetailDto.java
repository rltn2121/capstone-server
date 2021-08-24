package com.capstone.mountain.module.model.dto;

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

    private String totalTime;
    private String movingTime;
    private String restTime;
    private String distance;
    private String speed;
    private String height;
    private String calorie;
    private String date;

    @QueryProjection

    public RecordDetailDto(Long id, String name, String thumbnail, long totalTime, long movingTime, Double distance, Double speed, int height, int calorie, String date) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.totalTime = getTimeFromSecond(totalTime);
        this.movingTime = getTimeFromSecond(movingTime);
        this.restTime = getTimeFromSecond(totalTime - movingTime);
        this.restTime = restTime;
        this.distance = distance+"km";
        this.speed = speed+"km/h";
        this.height = height+"m";
        this.calorie = calorie+"kcal";
        this.date = date;
    }

    private String getTimeFromSecond(long time_sec) {
        long second = time_sec;
        long hour = second / 3600;
        second %= 3600;
        long minute = second / 60;
        second %= 60;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
