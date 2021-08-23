package com.capstone.mountain.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class NoDataMessage {
    private HttpStatus status;
    private String message;

    public NoDataMessage() {
        this.status = HttpStatus.FORBIDDEN;
        this.message = "로그인이 필요한 서비스입니다.";
    }
}
