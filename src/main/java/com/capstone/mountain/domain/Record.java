package com.capstone.mountain.domain;

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
public class Record {
    @Id
    @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

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
    private int calorie;
    private LocalDateTime date;
    @Column(columnDefinition = "TEXT")
    private String gpx_url;
    @Column(columnDefinition = "TEXT")
    private String thumbnail;
}
