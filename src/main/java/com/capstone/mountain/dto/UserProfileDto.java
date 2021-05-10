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
    private Long id;            // id
    private String nickname;    // 닉네임
    private int weight;         // 키
    private int height;         // 몸무게

    private Double dist_total;  // 총 등산거리
    private Double dist_avg;    // 평균 등산거리
    private String duration_total;// 총 등산 시간
    private String duration_avg;  // 평균 등산 시간
    private int height_total;// 총 오른 높이
    private int height_max;  // 최고 높이
    private int height_avg;  // 평균 높이
    private Double speed_avg;   // 평균 속도
    private Double speed_max;   // 최고 속도
    private int calorie_total;  // 총 소모 칼로리
    private int calorie_avg; // 평균 소모 칼로리

    @QueryProjection
    public UserProfileDto(Tuple tuple, String duration_total, String duration_avg) {
        this.id = tuple.get(0, Long.class);
        this.nickname = tuple.get(1, String.class);
        this.weight = tuple.get(2, Integer.class);
        this.height = tuple.get(3, Integer.class);
        this.dist_total = tuple.get(4, Double.class) == null?0.0:round(tuple.get(4, Double.class)*100)/100.0;
        this.dist_avg = tuple.get(5, Double.class)==null?0.0:round(tuple.get(5, Double.class)*100)/100.0;
        this.height_total = (tuple.get(6, Integer.class) == null ? 0 : tuple.get(6, Integer.class));
        this.height_max = tuple.get(7, Integer.class) == null ? 0 : tuple.get(7, Integer.class);
        this.height_avg = tuple.get(8, Double.class) == null ? 0 : (int)Math.round(tuple.get(8, Double.class));
        this.speed_avg = tuple.get(9, Double.class) == null ? 0.0 : round(tuple.get(9, Double.class)*100)/100.0;
        this.speed_max = tuple.get(10, Double.class) == null ? 0.0 : tuple.get(10, Double.class);
        this.calorie_total = tuple.get(11, Integer.class) == null ? 0 : (int)round(tuple.get(11, Integer.class));
        this.calorie_avg = tuple.get(12, Double.class) == null ? 0 : (int)round(tuple.get(12, Double.class));
        this.duration_total = duration_total;
        this.duration_avg = duration_avg;
    }
}
