package ru.ssau.webcaffe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data public class User {
    public static final String TABLE_NAME = "cff_user";
    public static final String PK_NAME = TABLE_NAME + "_id";
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
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(
            name = TABLE_NAME + "_role",
            joinColumns = @JoinColumn(name = PK_NAME)
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
