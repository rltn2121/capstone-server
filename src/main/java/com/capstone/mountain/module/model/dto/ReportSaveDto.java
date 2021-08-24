package com.capstone.mountain.module.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReportSaveDto {
    private Long course;
    private String reason;
}
