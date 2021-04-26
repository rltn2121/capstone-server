package com.capstone.mountain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HotMountain {
    @Id
    @GeneratedValue
    @Column(name = "hm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mountain_id")
    private Mountain mountain;

    private int rank;

    private LocalDateTime created_at;
}
