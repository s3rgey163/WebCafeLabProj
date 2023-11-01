package ru.ssau.webcaffe.util;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;

import javax.crypto.SecretKey;
import java.security.Key;

public class SecretKeyFactory {
    private final MacAlgorithm macAlgorithm;

    public SecretKeyFactory(MacAlgorithm macAlgorithm) {
        this.macAlgorithm = macAlgorithm;
    }

    public SecretKey newSecretKey() {
        return macAlgorithm.key().build();
    }

    public String newBase64SecretKey() {
        return Encoders.BASE64.encode(newSecretKey().getEncoded());
    }

    public static SecretKey hmacKeyOf(String keyString) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyString));
    }
}
