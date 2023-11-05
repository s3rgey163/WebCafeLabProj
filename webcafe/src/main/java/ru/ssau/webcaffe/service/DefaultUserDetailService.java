package ru.ssau.webcaffe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.User;
import ru.ssau.webcaffe.pojo.UserPojo;
import ru.ssau.webcaffe.repo.UserRepository;

import java.util.List;
import java.util.Set;

@Primary
@Service
public class DefaultUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public DefaultUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static String getUserNotFoundMsg(String user) {
        return "Пользователь[%s] не найден".formatted(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return UserPojo.ofEntity(userRepository.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                                getUserNotFoundMsg("email: " + username)
                        )
                )
        );
    }

    public UserPojo getUserById(long id) {
        User user = userRepository.getUsersById(id).orElse(null);
        return user != null ? UserPojo.ofEntity(user) : null;
    }

    private static UserPojo newUserPojo(User user) {
        return UserPojo.ofEntity(user);
    }
}
