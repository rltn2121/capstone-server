package com.capstone.mountain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MountainPreviewDto {
    private String thumbnail;
    private Long id;
    private String name;

    @QueryProjection

    public MountainPreviewDto(String thumbnail, Long id, String name) {
        this.thumbnail = thumbnail;
        this.id = id;
        this.name = name;
    }
}


