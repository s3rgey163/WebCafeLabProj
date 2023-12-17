package ru.ssau.webcaffe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.entity.Weight;
import ru.ssau.webcaffe.entity.WeightType;
import ru.ssau.webcaffe.pojo.*;
import ru.ssau.webcaffe.repo.AddressRepository;
import ru.ssau.webcaffe.repo.CustomerRepository;
import ru.ssau.webcaffe.service.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
public class WebcaffeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebcaffeApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}