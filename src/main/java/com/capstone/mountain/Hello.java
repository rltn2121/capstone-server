package com.capstone.mountain;

import lombok.Data;

@Data
public class Hello {
    public String hello;

    public Hello(String hello) {
        this.hello = hello;
    }
}
