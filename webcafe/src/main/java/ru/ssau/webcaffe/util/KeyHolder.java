package ru.ssau.webcaffe.util;

import io.jsonwebtoken.io.Encoders;

import javax.crypto.SecretKey;
import java.io.*;
import java.security.Key;

public record KeyHolder(Key key) {

    public void store(OutputStream stream) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(stream)) {
            byte[] rawKey = key.getEncoded();
            bos.write(rawKey, 0, rawKey.length);
        }
    }

    public String toBase64() {
        return Encoders.BASE64.encode(key.getEncoded());
    }
}
