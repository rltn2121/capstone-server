package com.capstone.mountain.module.model.dto;

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
    private Long user_id;       // id
    private String nickname;    // 닉네임
    private int user_height;
    private int user_weight;
    private String picture;

    private String total_distance;  // 총 등산거리
    private String avg_distance;    // 평균 등산거리
    private String max_distance;    // 최고 등산거리

    private String total_moving_time;// 총 이동 시간
    private String avg_moving_time;  // 평균 이동 시간
    private String max_moving_time;  // 최고 이동 시간

    private String total_total_time;// 총 시간
    private String avg_total_time;  // 평균 시간
    private String max_total_time;  // 최고 시간

    private String avg_speed;   // 평균 속도
    private String max_speed;   // 최고 속도

    private String avg_pace;   // 평균 속도
    private String max_pace;   // 최고 속도

    private String max_height;  // 최고 높이
    private String avg_height;   // 평균 높이

    private String total_total_uphill; // 총 오르막합
    private String avg_total_uphill;   // 평균 오르막합
    private String max_total_uphill;   // 최고 오르막합

    private String total_total_downhill; // 총 내리막합
    private String avg_total_downhill;   // 평균 내리막합
    private String max_total_downhill;   // 최고 내리막합

    private String total_calories;  // 총 소모 칼로리
    private String avg_calories; // 평균 소모 칼로리


    @QueryProjection

    public UserProfileDto(Long user_id, String nickname, int user_height, int user_weight, String picture, double total_distance, double avg_distance, double max_distance, long total_moving_time, double avg_moving_time, long max_moving_time, long total_total_time, double avg_total_time, long max_total_time, double avg_speed, double max_speed, double avg_pace, double max_pace, int max_height, double avg_height, int total_total_uphill, double avg_total_uphill, int max_total_uphill, int total_total_downhill, double avg_total_downhill, int max_total_downhill, int total_calories, double avg_calories) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.user_height = user_height;
        this.user_weight = user_weight;
        this.picture = (picture == null || picture.equals("")) ? "no_image" : picture;
        this.total_distance = round(total_distance*100)/100.0 + "km";
        this.avg_distance = round(avg_distance*100)/100.0 + "km";
        this.max_distance = round(max_distance*100)/100.0 + "km";




        this.total_moving_time = getTimeFromSecond(total_moving_time);
        this.avg_moving_time = getTimeFromSecond(round(avg_moving_time));
        this.max_moving_time = getTimeFromSecond(max_moving_time);
        this.total_total_time = getTimeFromSecond(total_total_time);
        this.avg_total_time = getTimeFromSecond(round(avg_total_time));
        this.max_total_time = getTimeFromSecond(max_total_time);
        this.avg_speed = round(avg_speed*100)/100.0 + "km/h";
        this.max_speed = round(max_speed*100)/100.0 + "km/h";
        this.avg_pace = round(avg_pace*100)/100.0 + "분/km";
        this.max_pace = round(max_pace*100)/100.0 + "분/km";
        this.max_height = round(max_height) + "m";
        this.avg_height = round(avg_height) + "m";
        this.total_total_uphill = round(total_total_uphill) + "m";
        this.avg_total_uphill = round(avg_total_uphill) + "m";
        this.max_total_uphill = round(max_total_uphill) + "m";
        this.total_total_downhill = round(total_total_downhill) + "m";
        this.avg_total_downhill = round(avg_total_downhill) + "m";
        this.max_total_downhill = round(max_total_downhill) + "m";
        this.total_calories = round(total_calories) + "kcal";
        this.avg_calories = round(avg_calories) + "kcal";
    }

    private String getTimeFromSecond(long time_sec) {
        long second = time_sec;
        long hour = second / 3600;
        second %= 3600;
        long minute = second / 60;
        second %= 60;

        return hour + "시간 " + minute + "분 " + second + "초";
    }

}
