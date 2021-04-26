package com.capstone.mountain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(length=50)
    private String username;

    @Column(columnDefinition = "TEXT")
    private String password;

    @Column(length=45)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(columnDefinition="TEXT")
    private String token;

    private int weight;
    private int height;

    private String roles;

    public List<String> getRoleList() {
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
