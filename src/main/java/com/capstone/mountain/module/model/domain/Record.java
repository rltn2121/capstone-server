package com.capstone.mountain.module.model.domain;

import com.capstone.mountain.module.model.dto.RecordUploadDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    public Record(User user, Course course, RecordUploadDto recordUploadDto){
        this.user = user;
        this.course = course;
        this.title = recordUploadDto.getTitle();
        this.filename = recordUploadDto.getFilename();
        this.distance = recordUploadDto.getDistance();
        this.moving_time_str = recordUploadDto.getMoving_time_str();
        this.total_time_str = recordUploadDto.getTotal_time_str();
        this.moving_time_sec = recordUploadDto.getMoving_time_sec();
        this.total_time_sec = recordUploadDto.getTotal_time_sec();
        this.avg_speed = recordUploadDto.getAvg_speed();
        this.avg_pace = recordUploadDto.getAvg_pace();
        this.location = recordUploadDto.getLocation();
        this.latitude = recordUploadDto.getLatitude();
        this.longitude = recordUploadDto.getLongitude();
        this.max_height = recordUploadDto.getMax_height();
        this.min_height = recordUploadDto.getMin_height();
        this.ele_dif = recordUploadDto.getEle_dif();
        this.total_uphill = recordUploadDto.getTotal_uphill();
        this.total_downhill = recordUploadDto.getTotal_downhill();
        this.difficulty = recordUploadDto.getDifficulty();
        this.calorie = recordUploadDto.getCalorie();
        this.date = recordUploadDto.getDate();
        this.gpx_url = recordUploadDto.getGpx_url();
        this.thumbnail = recordUploadDto.getThumbnail();
    }
}
