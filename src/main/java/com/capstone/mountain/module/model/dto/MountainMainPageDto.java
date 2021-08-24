package com.capstone.mountain.module.model.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MountainMainPageDto {
    private String thumbnail;
    private Long id;
    private String name;

    @QueryProjection
    public MountainMainPageDto(String thumbnail, Long id, String name){
        this.thumbnail = thumbnail;
        this.id = id;
        this.name = name;
    }

}


