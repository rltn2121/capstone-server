package com.capstone.mountain;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Message {

    private HttpStatus status;
    private String message;
    private Object data;

    public Message() {
        this.status = HttpStatus.FORBIDDEN;
        this.data = null;
        this.message = "로그인이 필요한 서비스입니다.";
    }

    public Message(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}