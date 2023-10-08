package ru.ssau.webcaffe.util;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
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

    public static <T> T cloneContext(Supplier<T> supplier, Object obj) {
        T t = supplier.get();
        for(var field : t.getClass().getDeclaredFields()) {
            try {
                var srcField = obj.getClass().getDeclaredField(field.getName());
                field.setAccessible(true);
                srcField.setAccessible(true);
                field.set(t, srcField.get(obj));
            } catch (NoSuchFieldException e) {
                System.out.printf("Not found %s.%s in class %s\n",
                        t.getClass().getName(),
                        field.getName(),
                        obj.getClass()
                );
            } catch (IllegalAccessException e) {
                System.err.println(e);
            }

        }
        return t;
    }

    public static void main(String[] args) {
        var l1 = new Object() {
            private Set<Integer> ints = Set.of(1,2,3,4,5,6);
            private String name = "Hello world";
            private int j = 1;

            @Override
            public String toString() {
                return "$classname{" +
                        "ints=" + ints +
                        ", name='" + name + '\'' +
                        ", j=" + j +
                        '}';
            }
        };

        var l2 = new Object() {
            private Set<Integer> ints;
            private String name;

            @Override
            public String toString() {
                return "$classname{" +
                        "ints=" + ints +
                        ", name='" + name + '\'' +
                        '}';
            }
        };

        System.out.println(Util.cloneContext(() -> l2, l1));
        System.out.println(l1.ints.hashCode() + " " + l2.ints.hashCode());
    }
}
