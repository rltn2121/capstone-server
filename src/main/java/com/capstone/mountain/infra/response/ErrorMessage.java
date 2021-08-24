package com.capstone.mountain.infra.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessage {
    private HttpStatus status;
    private String message;

    public ErrorMessage() {
        this.status = HttpStatus.FORBIDDEN;
        this.message = "로그인이 필요한 서비스입니다.";
    }
}
