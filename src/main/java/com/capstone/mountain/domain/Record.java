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

    public Record(User user, Course course, String title, String filename, Double distance, String moving_time_str, String total_time_str, int moving_time_sec, int total_time_sec, Double avg_speed, Double avg_pace, String location, Double latitude, Double longitude, int max_height, int min_height, int ele_dif, int total_uphill, int total_downhill, String difficulty, int calorie, LocalDateTime date, String gpx_url, String thumbnail) {
        this.user = user;
        this.course = course;
        this.title = title;
        this.filename = filename;
        this.distance = distance;
        this.moving_time_str = moving_time_str;
        this.total_time_str = total_time_str;
        this.moving_time_sec = moving_time_sec;
        this.total_time_sec = total_time_sec;
        this.avg_speed = avg_speed;
        this.avg_pace = avg_pace;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.max_height = max_height;
        this.min_height = min_height;
        this.ele_dif = ele_dif;
        this.total_uphill = total_uphill;
        this.total_downhill = total_downhill;
        this.difficulty = difficulty;
        this.calorie = calorie;
        this.date = date;
        this.gpx_url = gpx_url;
        this.thumbnail = thumbnail;
    }
}
