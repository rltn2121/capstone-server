package com.capstone.mountain.dto;

import com.querydsl.core.Tuple;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.lang.Math;

import java.sql.Time;

import static java.lang.Math.round;


@Data
@NoArgsConstructor
@ToString
public class UserProfileDto {
    private Long user_id;            // id
    private String nickname;    // 닉네임
    private String user_height_weight;
    private String total_distance;  // 총 등산거리
    private String avg_distance;    // 평균 등산거리
    private String total_time;// 총 등산 시간
    private String avg_time;  // 평균 등산 시간
    private String total_height;// 총 오른 높이
    private String max_height;  // 최고 높이
    private String avg_height;  // 평균 높이
    private String avg_speed;   // 평균 속도
    private String max_speed;   // 최고 속도
    private String total_calories;  // 총 소모 칼로리
    private String avg_calories; // 평균 소모 칼로리
    private String picture;


    @QueryProjection
    public UserProfileDto(Tuple tuple, String duration_total, String duration_avg) {
        this.user_id = tuple.get(0, Long.class);
        this.nickname = tuple.get(1, String.class);
        this.user_height_weight = (tuple.get(3, Integer.class)==0?"-":tuple.get(3, Integer.class))
                + "cm / "
                + (tuple.get(2, Integer.class)==0?"-":tuple.get(2, Integer.class)) + "kg";
        this.total_distance = (tuple.get(4, Double.class) == null?0.0:round(tuple.get(4, Double.class)*100)/100.0 )+ "km";
        this.avg_distance = (tuple.get(5, Double.class)==null?0.0:round(tuple.get(5, Double.class)*100)/100.0)+ "km";
        this.total_height = (tuple.get(6, Integer.class) == null ? 0 : tuple.get(6, Integer.class))+ "m";
        this.max_height = (tuple.get(7, Integer.class) == null ? 0 : tuple.get(7, Integer.class))+ "m";
        this.avg_height = (tuple.get(8, Double.class) == null ? 0 : (int)Math.round(tuple.get(8, Double.class)))+ "m";
        this.avg_speed = (tuple.get(9, Double.class) == null ? 0.0 : round(tuple.get(9, Double.class)*100)/100.0)+ "km/h";
        this.max_speed = (tuple.get(10, Double.class) == null ? 0.0 : tuple.get(10, Double.class))+ "km/h";
        this.total_calories = (tuple.get(11, Integer.class) == null ? 0 : (int)round(tuple.get(11, Integer.class))) + "kcal";
        this.avg_calories = (tuple.get(12, Double.class) == null ? 0 : (int)round(tuple.get(12, Double.class)))+"kcal";
        this.picture = tuple.get(13, String.class);
        this.total_time = duration_total;
        this.avg_time = duration_avg;
    }
}
