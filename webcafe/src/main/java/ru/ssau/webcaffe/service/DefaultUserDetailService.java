package ru.ssau.webcaffe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.pojo.User;
import ru.ssau.webcaffe.repo.UserRepository;

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
        return User.ofEntity(userRepository.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                                getUserNotFoundMsg("email: " + username)
                        )
                )
        );
    }

    public User getUserById(long id) {
        ru.ssau.webcaffe.entity.User user = userRepository.getUsersById(id).orElse(null);
        return user != null ? User.ofEntity(user) : null;
    }

    private static User newUserPojo(ru.ssau.webcaffe.entity.User user) {
        return User.ofEntity(user);
    }
}
