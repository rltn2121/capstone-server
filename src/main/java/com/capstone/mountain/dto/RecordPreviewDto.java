package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@ToString
public class RecordPreviewDto {
    private String name;
    private Double distance;
    private int height;
    private LocalTime time;
    private Double calorie;
    private String thumbnail;

    @QueryProjection

    public RecordPreviewDto(String name, Double distance, int height, LocalTime time, Double calorie, String thumbnail) {
        this.name = name;
        this.distance = distance;
        this.height = height;
        this.time = time;
        this.calorie = calorie;
        this.thumbnail = thumbnail;
    }
}
