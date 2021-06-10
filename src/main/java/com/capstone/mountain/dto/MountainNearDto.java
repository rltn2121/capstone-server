package com.capstone.mountain.dto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MountainNearDto {
    private String thumbnail;
    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    @QueryProjection
    public MountainNearDto(String thumbnail, Long id, String name, double latitude, double longitude) {
        this.thumbnail = thumbnail;
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }




}


