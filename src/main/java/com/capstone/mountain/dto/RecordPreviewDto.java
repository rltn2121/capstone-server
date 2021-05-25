package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@ToString
public class RecordPreviewDto {
    private Long id;
    private String name;
    private String distance;
    private String height;
    private String time;
    private String calorie;
    private String thumbnail;

    @QueryProjection

    public RecordPreviewDto(Long id, String name, Double distance, int height, String time, int calorie, String thumbnail) {
        this.id = id;
        this.name = name;
        this.distance = distance+"km";
        this.height = height+"m";
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3,5));

        this.time = hour + "시간 " + minute + "분";
        this.calorie = calorie + "kcal";
        this.thumbnail = thumbnail;
    }
}
