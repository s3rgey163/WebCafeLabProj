package ru.ssau.webcaffe;

import org.hibernate.Hibernate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.webcaffe.entity.*;
import ru.ssau.webcaffe.pojo.OrderPositionPojo;
import ru.ssau.webcaffe.pojo.PromotionPojo;
import ru.ssau.webcaffe.repo.PromotionRepository;
import ru.ssau.webcaffe.service.RepositoryService;
import ru.ssau.webcaffe.util.Util;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class WebcaffeApplication {

    public static void main(String[] args) {
        var l = Stream.of(new int[]{1,2,3}, new int[] {4,5,6}, new int[]{7,8,9}).map(ints -> ints.length).toArray();
        System.out.println(Arrays.toString(l));
        RepositoryService.RepositoryResultHandler<Promotion> result = new RepositoryService.RepositoryResultHandler<>(new Promotion());
        result.doBeforeCloseSession(Hibernate::initialize)
                .thenApply(PromotionPojo::ofEntity);
    }

}
