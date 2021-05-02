package com.capstone.mountain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AccessTokenDto {
    private final String access_token;
}
