package ru.ssau.webcaffe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.entity.User;
import ru.ssau.webcaffe.exception.EntityPersistenceException;
import ru.ssau.webcaffe.payload.request.SignupRequest;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.pojo.UserPojo;
import ru.ssau.webcaffe.repo.AddressRepository;
import ru.ssau.webcaffe.repo.UserRepository;

import java.security.Principal;
import java.util.Set;

@Service
public class UserService {
    public static final Logger lg = LoggerFactory.getLogger(UserService.class);

    public static final Set<ru.ssau.webcaffe.entity.User.AuthRole> DEFAULT_ROLES = Set.of(ru.ssau.webcaffe.entity.User.AuthRole.USER);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder pswdEncoder;

    private final AddressRepository addressRepository;

    @Autowired
    public UserService(UserRepository userRepository, AddressRepository addressRepository, BCryptPasswordEncoder pswdEncoder) {
        this.userRepository = userRepository;
        this.pswdEncoder = pswdEncoder;
        this.addressRepository = addressRepository;
    }

    public UserPojo createUser(SignupRequest signupRequest) {
        var customer = CustomerPojo.builder()
                .name(signupRequest.getFirstname())
                .secondName(signupRequest.getSecondname())
                .middleName(signupRequest.getMiddlename())
                .birthday(signupRequest.getBirthday()).build();
        return UserPojo.builder()
                .withLogin(signupRequest.getUsername())
                .withEmail(signupRequest.getEmail())
                .withGender(signupRequest.getGender())
                .withPassword(pswdEncoder.encode(signupRequest.getPassword()))
                .withCustomer(customer)
                .withAuthRole(DEFAULT_ROLES).build();
    }

    public void saveUser(UserPojo userPojo) {
        lg.debug("Save user: {}", userPojo);
        try {
            User userEntity = userPojo.toEntity();
            userEntity.getCustomer().setUser(userEntity);
            addressRepository.saveAll(userEntity.getCustomer().getAddresses());
            userRepository.save(userEntity);
        } catch (Exception ex) {
            lg.warn("Unable to save user[login: {}, email: {}]. Cause: ",
                    userPojo.getLogin(),
                    userPojo.getEmail(),
                    ex
            );
            throw new EntityPersistenceException("The user[login: %s, email: %s] already exist"
                    .formatted(userPojo.getLogin(), userPojo.getEmail()),
                    ex
            );
        }
    }

    public UserPojo saveUser(SignupRequest signupRequest) {
        var user = createUser(signupRequest);
        saveUser(user);
        return user;
    }

    public UserPojo update(UserPojo newUser, Principal oldUserPrincipal) {
        UserPojo oldUser = getUserByPrincipal(oldUserPrincipal);
        oldUser.setCustomer(newUser.getCustomer());
        oldUser.setGender(newUser.getGender());

        userRepository.save(oldUser.toEntity());
        return oldUser;
    }

    public UserPojo getUserByPrincipal(Principal principal) {
       User user = userRepository.getUserByEmail(principal.getName())
                .orElseThrow(() ->
                        new EntityPersistenceException("Unable find user with name: " + principal.getName())
                );
        return UserPojo.ofEntity(user);
    }

    public UserPojo getUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityPersistenceException("User with id[%d] not found".formatted(userId))
        );
        return UserPojo.ofEntity(user);
    }
}
