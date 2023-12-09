package ru.ssau.webcaffe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.exception.EntityPersistenceException;
import ru.ssau.webcaffe.payload.request.SignupRequest;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.pojo.User;
import ru.ssau.webcaffe.repo.UserRepository;

import java.security.Principal;
import java.util.Set;

@Service
public class UserService {
    public static final Logger lg = LoggerFactory.getLogger(UserService.class);

    public static final Set<ru.ssau.webcaffe.entity.User.AuthRole> DEFAULT_ROLES = Set.of(ru.ssau.webcaffe.entity.User.AuthRole.USER);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder pswdEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder pswdEncoder) {
        this.userRepository = userRepository;
        this.pswdEncoder = pswdEncoder;
    }

    public User createUser(SignupRequest signupRequest) {
        var customer = CustomerPojo.builder()
                .name(signupRequest.getFirstname())
                .secondName(signupRequest.getSecondname())
                .middleName(signupRequest.getMiddlename())
                .birthday(signupRequest.getBirthday()).build();
        return User.builder()
                .withLogin(signupRequest.getUsername())
                .withEmail(signupRequest.getEmail())
                .withGender(signupRequest.getGender())
                .withPassword(pswdEncoder.encode(signupRequest.getPassword()))
                .withCustomer(customer)
                .withAuthRole(DEFAULT_ROLES).build();
    }

    public User saveUser(SignupRequest signupRequest) {
        var user = createUser(signupRequest);
        lg.debug("Save user: {}", user);
        try {
            ru.ssau.webcaffe.entity.User userEntity = user.toEntity();
            userEntity.getCustomer().setUser(userEntity);
            userRepository.save(userEntity);
        } catch (Exception ex) {
            lg.warn("Unable to save user[login: {}, email: {}]. Cause: ",
                    user.getLogin(),
                    user.getEmail(),
                    ex
            );
            throw new EntityPersistenceException("The user[login: %s, email: %s] already exist"
                    .formatted(user.getLogin(), user.getEmail()),
                    ex
            );
        }
        return user;
    }

    public User update(User newUser, Principal oldUserPrincipal) {
        User oldUser = getUserByPrincipal(oldUserPrincipal);
        oldUser.setCustomer(newUser.getCustomer());
        oldUser.setGender(newUser.getGender());

        userRepository.save(oldUser.toEntity());
        return oldUser;
    }

    public User getUserByPrincipal(Principal principal) {
        ru.ssau.webcaffe.entity.User user = userRepository.getUserByEmail(principal.getName())
                .orElseThrow(() ->
                        new EntityPersistenceException("Unable find user with name: " + principal.getName())
                );
        return User.ofEntity(user);
    }
}
