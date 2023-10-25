package ru.ssau.webcaffe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.webcaffe.entity.*;
import ru.ssau.webcaffe.repo.UserRepository;


@SpringBootApplication
public class WebcaffeApplication {

    private static UserRepository ur;

    public WebcaffeApplication(UserRepository ur) {
        this.ur = ur;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebcaffeApplication.class, args);
    }
}