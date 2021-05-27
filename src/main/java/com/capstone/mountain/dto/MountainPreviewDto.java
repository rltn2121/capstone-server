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
public class MountainPreviewDto {
    private Long id;
    private String name;
    private String height;
    private String location;
    private String thumbnail;

    @QueryProjection
    public MountainPreviewDto(Long id, String name, int height, String location, String thumbnail) {
        this.id = id;
        this.name = name;
        this.height = height+"m";
        this.location = location;
        this.thumbnail = thumbnail;
    }
}
