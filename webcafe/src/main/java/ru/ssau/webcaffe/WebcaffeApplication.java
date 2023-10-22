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
    }
}