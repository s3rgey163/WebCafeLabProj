package ru.ssau.webcaffe.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import ru.ssau.webcaffe.pojo.UserPojo;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public interface JWTTokenProvider {


    boolean validateToken(String token);

    JwtParser getJwtParser();

    long getUserIdByToken(String token);

    Key getAccessKey();

    default String generateToken(Authentication authentication) {
        UserPojo user = (UserPojo) authentication.getPrincipal();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + (long) SecurityAttributes.EXPIRATION_TIME.getValue());
        Map<String, String> claims = Map.of(
                "id", String.valueOf(user.getId()),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "firstname", user.getCustomer().getName(),
                "secondname", user.getCustomer().getSecondName(),
                "middlename", user.getCustomer().getMiddleName()
        );
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claims(claims)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getAccessKey())
                .compact();
    }

    default Claims getClaims(String token) {
        return getJwtParser()
                .parseSignedClaims(token)
                .getPayload();
    }

}
