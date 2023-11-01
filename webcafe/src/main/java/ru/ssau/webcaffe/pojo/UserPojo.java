package ru.ssau.webcaffe.pojo;

import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
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

@Builder(setterPrefix = "with")
@Data
public class UserPojo implements UserDetails  {
    private long id;

    private String login;

    private String password;

    private String email;

    private User.Gender gender;

    private Set<User.AuthRole> authRole;

    private LocalDateTime created;

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
        return true;
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

    private static List<? extends GrantedAuthority> getAuthoritiesByUserRoles(Set<User.AuthRole> roles) {
        return roles.stream()
                .map(User.AuthRole::name)
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
                .withCustomer(customer.toEntity())
                .withAuthRole(authRole)
                .withCreated(created)
                .build();
    }
}
