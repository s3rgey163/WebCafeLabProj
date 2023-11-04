package ru.ssau.webcaffe.util;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.stream.Collectors;

public class SecretKeyFactory {
    private final MacAlgorithm macAlgorithm;

    public SecretKeyFactory(MacAlgorithm macAlgorithm) {
        this.macAlgorithm = macAlgorithm;
    }


    public KeyHolder newSecretKey() {
        return new KeyHolder(macAlgorithm.key().build());
    }

    public static KeyHolder OfBase64(String keyString) {
        return new KeyHolder(Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyString)));
    }

    public static KeyHolder load(InputStream is) throws IOException {
        try(BufferedReader bis = new BufferedReader(new InputStreamReader(is))) {
            return SecretKeyFactory.OfBase64(
                    bis.lines().collect(Collectors.joining())
            );
        }
    }
}
