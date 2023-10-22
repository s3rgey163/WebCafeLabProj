package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table
@Data public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Gender gender;
    @Column(nullable = false)
    private LocalDateTime created;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
    }



    @Getter
    public enum Gender {
        MALE("Мужчина"),
        FEMALE("Женщина"),
        ;

        private final String name;

        Gender(String name) {
            this.name = name;
        }
    }

    @Getter
    public enum Role {
        USER("Пользователь"),
        ADMIN("Администратор"),
        ;

        private final String name;

        Role(String name) {
            this.name = name;
        }
    }

}
