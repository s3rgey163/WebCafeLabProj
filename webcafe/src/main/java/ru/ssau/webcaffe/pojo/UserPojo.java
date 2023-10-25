package ru.ssau.webcaffe.pojo;

import lombok.Builder;
import lombok.Data;
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

    private Set<User.AuthRole> authRole;

    private LocalDateTime created;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPojo ofEntity(User user) {
        return UserPojo.builder()
                .withId(user.getId())
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withEmail(user.getEmail())
                .withGender(user.getGender())
                .withAuthRole(user.getAuthRole())
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
                authRole,
                created
        );
    }
}
