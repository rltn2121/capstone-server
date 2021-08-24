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
public class CourseMainPageDto {
    private Long id;
    private String name;
    private String distance;
    private String moving_time;
    private String difficulty;
    private String thumbnail;

    @QueryProjection
    public CourseMainPageDto(Long id, String name, Double distance, String moving_time, String difficulty, String thumbnail) {
        this.id = id;
        this.name = (name.length() > 20 ? name.substring(0, 17) + "..." : name);
        this.distance = distance+"km";
        int hour = Integer.parseInt(moving_time.substring(0, 2));
        int minute = Integer.parseInt(moving_time.substring(3,5));

        this.moving_time = hour + "시간 " + minute + "분";
        this.difficulty = difficulty;
        this.thumbnail = thumbnail;
    }
}
