package ru.ssau.webcaffe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = User.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
@Getter
@Setter
@ToString
public class User {
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Customer customer;

    @ElementCollection(targetClass = AuthRole.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = TABLE_NAME + "_role",
            joinColumns = @JoinColumn(name = PK_NAME)
    )
    @Enumerated(EnumType.STRING)
    private Set<AuthRole> authRole;

    @Column(
            unique = true,
            nullable = false,
            updatable = false
    )
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

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
    public enum AuthRole implements GrantedAuthority{
        USER("Пользователь"),
        ADMIN("Администратор"),
        ;

        private final String name;

        AuthRole(String name) {
            this.name = name;
        }

        @Override
        public String getAuthority() {
            return this.name();
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
