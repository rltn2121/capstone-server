package com.capstone.mountain.module.model.dto;

import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RecordUploadDto {
    private Long course;
    private String title;

    private String filename;
    private Double distance;
    private String moving_time_str;
    private String total_time_str;
    private int moving_time_sec;
    private int total_time_sec;

    private Double avg_speed;
    private Double avg_pace;
    private String location;
    private Double latitude;
    private Double longitude;

    private int max_height;
    private int min_height;
    private int ele_dif;
    private int total_uphill;
    private int total_downhill;
    private String difficulty;
    private int calorie;
    private LocalDateTime date;
    private String gpx_url;
    private String thumbnail;
}
