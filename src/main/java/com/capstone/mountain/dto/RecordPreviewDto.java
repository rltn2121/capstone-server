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
    private Double distance;
    private int height;
    private String time;
    private int calorie;
    private String thumbnail;

    @QueryProjection

    public RecordPreviewDto(Long id, String name, Double distance, int height, String time, int calorie, String thumbnail) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.height = height;
        //this.time = time.format(DateTimeFormatter.ofPattern("h시간 mm분 ss초"));
        this.time = time;
        this.calorie = calorie;
        this.thumbnail = thumbnail;
    }
}
