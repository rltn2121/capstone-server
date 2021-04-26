package com.capstone.mountain.domain;

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

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(length=200)
    private String contents;

    @Column(length=200)
    private String location;
}
