package ru.ssau.webcaffe.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ssau.webcaffe.entity.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link User}
 */
@Builder(setterPrefix = "with")
@Data
public class UserPojo implements UserDetails  {
    private long id;

    private String login;

    private String password;

    private String email;

    private User.Gender gender;

    private Set<User.AuthRole> authRole;

    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

    private CustomerPojo customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authRole;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static List<? extends GrantedAuthority> getAuthoritiesByUserRoles(Set<ru.ssau.webcaffe.entity.User.AuthRole> roles) {
        return roles.stream()
                .map(ru.ssau.webcaffe.entity.User.AuthRole::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public static UserPojo ofEntity(User user) {
        return UserPojo.builder()
                .withId(user.getId())
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withEmail(user.getEmail())
                .withGender(user.getGender())
                .withCustomer(CustomerPojo.ofEntity(user.getCustomer()))
                .withAuthRole(user.getAuthRole())
                .withCreated(user.getCreated())
                .build();

    }

    public User toEntity() {
        return User.builder()
                .withId(id)
                .withLogin(login)
                .withPassword(password)
                .withEmail(email)
                .withGender(gender)
                .withCustomer(customer == null ? null : customer.toEntity())
                .withAuthRole(authRole)
                .withCreated(created)
                .build();
    }
}
