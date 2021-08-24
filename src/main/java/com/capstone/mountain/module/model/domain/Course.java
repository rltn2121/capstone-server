package com.capstone.mountain.module.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    @Column(length = 50)
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
    private LocalDateTime date;
    @Column(columnDefinition = "TEXT")
    private String gpx_url;
    @Column(columnDefinition = "TEXT")
    private String thumbnail;
}
