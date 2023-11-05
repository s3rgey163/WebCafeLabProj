package ru.ssau.webcaffe.util;

import com.google.gson.Gson;
import jakarta.jws.Oneway;
import org.apache.logging.slf4j.SLF4JLoggerContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ssau.webcaffe.pojo.UserPojo;
import ru.ssau.webcaffe.security.JWTHmac512TokenProvider;
import ru.ssau.webcaffe.security.JWTTokenProvider;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {
    private static final Logger lg = LoggerFactory.getLogger(Util.class);

    private Util() {}

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

    public static <T> T cloneContext(Object fromObj, Supplier<T> toObj) {
        T t = toObj.get();
        for(var field : t.getClass().getDeclaredFields()) {
            try {
                Field srcField = fromObj.getClass().getDeclaredField(field.getName());
                if(!srcField.getType().isPrimitive()) {
                    lg.warn("Copy context not implemented for non primitive field types. Skip - {}.{}:{}",
                            fromObj.getClass().getSimpleName(),
                            srcField.getName(),
                            srcField.getType().getSimpleName()
                    );
                    continue;
                }
                field.setAccessible(true);
                srcField.setAccessible(true);
                field.set(t, srcField.get(fromObj));
            } catch (NoSuchFieldException e) {
                lg.debug("Not found {}.{} in class {}",
                        t.getClass().getName(),
                        field.getName(),
                        fromObj.getClass()
                );
            } catch (IllegalAccessException e) {
                lg.error(e.getMessage());
            }

        }
        return t;
    }

    public static <T, R, C extends Collection<R>> C collectionMapper(
            Collection<T> original,
            Function<T, R> mapper,
            Supplier<C> newCollection
    ) {
        Objects.requireNonNull(mapper);
        Objects.requireNonNull(original);
        return original.stream()
                .map(mapper)
                .collect(newCollection, Collection::add, Collection::addAll);
    }

    public static void main(String[] args) {
        System.out.println(UserPojo.builder().withId(1).build());
        var l1 = new Object() {
            private Set<Integer> ints = Set.of(1,2,3,4,5,6);
            private String name = "Hello world";

            private int i = 123123;
            private int j = 23;

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

            private int i = 0;


            @Override
            public String toString() {
                return "$classname{" +
                        "ints=" + ints +
                        ", name='" + name + '\'' +
                        ", i=" + i +
                        '}';
            }
        };
        l2.ints = Set.of(1,2,3);
        l2.name = "Hi";
        System.out.println(Util.cloneContext(l1, () -> l2));
        System.out.println(l1.ints.hashCode() + " " + l2.ints.hashCode());
    }
}
