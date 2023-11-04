package ru.ssau.webcaffe.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.ssau.webcaffe.util.SecretKeyFactory;

import javax.crypto.SecretKey;
import java.security.Key;

@Primary
@Component("JWTHmac512TokenProvider")
public class JWTHmac512TokenProvider implements JWTTokenProvider {
    public static final Logger lg = LoggerFactory.getLogger(JWTHmac512TokenProvider.class);

    private final SecretKey accessKey;

    public JWTHmac512TokenProvider(@Value("${jwt.secret}") String accessKey) {
        this.accessKey = (SecretKey) SecretKeyFactory.OfBase64(accessKey).key();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(accessKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            lg.debug("Error validate token: {}. Cause: ", token, ex);
            return false;
        }
    }

    @Override
    public JwtParser getJwtParser() {
        return Jwts.parser().verifyWith(accessKey).build();
    }

    @Override
    public long getUserIdByToken(String token) {
        return getClaims(token).get("id", Long.class);
    }

    @Override
    public Key getAccessKey() {
        return accessKey;
    }
}
