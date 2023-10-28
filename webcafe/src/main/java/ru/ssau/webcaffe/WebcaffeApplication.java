package ru.ssau.webcaffe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.webcaffe.entity.*;
import ru.ssau.webcaffe.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TreeSet;


@SpringBootApplication
public class WebcaffeApplication implements CommandLineRunner {

    @Autowired
    EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(WebcaffeApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        Query query = entityManager.createQuery("select day(cast(:d as localdatetime))");
        query.setParameter("d", LocalDateTime.now());
        System.out.println(query.getSingleResult());
    }
}