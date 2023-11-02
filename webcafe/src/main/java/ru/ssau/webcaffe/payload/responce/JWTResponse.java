package ru.ssau.webcaffe.payload.responce;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JWTResponse {
    private final String type = "Bearer";

    private String accessToken;
    private boolean isSuccess;
}