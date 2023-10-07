package ru.ssau.webcaffe.util;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {
    private Util() {}

//    public static String generateRandomString(int length) {
//        final var baseReference = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//    }

    public static String generateRandomString(
            CharSequence referenceRange,
            int length
    ) {
        var rnd = new SecureRandom(ByteBuffer.allocate(Long.BYTES)
                .putLong(new Date().getTime())
                .array());
        return IntStream.range(0, length)
                .map(i -> rnd.nextInt(referenceRange.length()))
                .mapToObj(random -> String.valueOf(referenceRange.charAt(random)))
                .collect(Collectors.joining());
    }
}
