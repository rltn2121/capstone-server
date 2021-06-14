package com.capstone.mountain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class MainDto {
    private String nickname;
    private List<CourseMainPageDto> recommendCourse = new ArrayList<>();
    private List<CourseMainPageDto> hotCourse = new ArrayList<>();
    private List<MountainMainPageDto> hotMountain = new ArrayList<>();
    private List<MountainMainPageDto> nearMountain = new ArrayList<>();
}
