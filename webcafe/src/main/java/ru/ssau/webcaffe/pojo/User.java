package ru.ssau.webcaffe.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Builder(setterPrefix = "with")
@Data
public class User implements UserDetails  {
    private long id;

    private String login;

    private String password;

    private String email;

    private ru.ssau.webcaffe.entity.User.Gender gender;

    private Set<ru.ssau.webcaffe.entity.User.AuthRole> authRole;

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

    private static List<? extends GrantedAuthority> getAuthoritiesByUserRoles(Set<ru.ssau.webcaffe.entity.User.AuthRole> roles) {
        return roles.stream()
                .map(ru.ssau.webcaffe.entity.User.AuthRole::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public static User ofEntity(ru.ssau.webcaffe.entity.User user) {
        return User.builder()
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

    public ru.ssau.webcaffe.entity.User toEntity() {
        return ru.ssau.webcaffe.entity.User.builder()
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
