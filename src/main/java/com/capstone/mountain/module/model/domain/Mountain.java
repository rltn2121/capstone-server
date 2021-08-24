package com.capstone.mountain.module.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mountain {
    @Id
    @GeneratedValue
    @Column(name = "mountain_id")
    private Long id;

    @Column(length = 10)
    private String name;

    private int height;

    @Column(length=200)
    private String location;

    private int spring;
    private int summer;
    private int fall;
    private int winter;

    private double latitude;
    private double longitude;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

}
