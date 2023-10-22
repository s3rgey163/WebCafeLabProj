package ru.ssau.webcaffe.pojo;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import ru.ssau.webcaffe.entity.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Builder(setterPrefix = "with")
@Data
public class UserPojo {
    private long id;

    private String login;

    private String password;

    private String email;

    private User.Gender gender;

    private Set<User.Role> role;

    private LocalDateTime created;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPojo ofEntity(User user) {
        return UserPojo.builder()
                .withId(user.getId())
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withEmail(user.getEmail())
                .withGender(user.getGender())
                .withRole(user.getRole())
                .withCreated(user.getCreated())
                .build();
    }

    public User toEntity() {
        return new User(
                id,
                login,
                password,
                email,
                gender,
                role,
                created
        );
    }
}
