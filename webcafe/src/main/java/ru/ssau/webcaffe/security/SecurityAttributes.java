package ru.ssau.webcaffe.security;

import lombok.Getter;

import java.util.Base64;

@Getter
public enum SecurityAttributes {
    SIGN_UP_URLS("/api/auth/**"),
    RAW_SECRET("SecretKeyGenJWT"),
    TOKEN_PREFIX("Bearer "),
    HEADER_STRING("Authorization"),
    CONTENT_TYPE("application/json"),
    EXPIRATION_TIME(864_000_000L)
    ;


    private final Object value;

    SecurityAttributes(Object value) {
        this.value = value;
    }
}
