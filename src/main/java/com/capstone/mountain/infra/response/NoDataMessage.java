package com.capstone.mountain.infra.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class NoDataMessage {
    private String status;
    private String message;
}
