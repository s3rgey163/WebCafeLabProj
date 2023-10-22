package ru.ssau.webcaffe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

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

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<Role> role;

    @Column(
            unique = true,
            nullable = false,
            updatable = false
    )
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime created;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

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
