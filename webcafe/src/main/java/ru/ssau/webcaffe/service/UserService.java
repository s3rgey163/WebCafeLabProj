package ru.ssau.webcaffe.service;

import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.User;
import ru.ssau.webcaffe.exception.EntityPersistenceException;
import ru.ssau.webcaffe.payload.request.SignupRequest;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.pojo.UserPojo;
import ru.ssau.webcaffe.repo.AddressRepository;
import ru.ssau.webcaffe.repo.UserRepository;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    public static final Logger lg = LoggerFactory.getLogger(UserService.class);

    public static final Set<ru.ssau.webcaffe.entity.User.AuthRole> DEFAULT_ROLES = Set.of(ru.ssau.webcaffe.entity.User.AuthRole.ROLE_USER);

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
                .birthday(signupRequest.getBirthday())
                .orderPojos(new HashSet<>(0))
                .addressPojos(new HashSet<>(0))
                .build();
        return UserPojo.builder()
                .withLogin(signupRequest.getUsername())
                .withEmail(signupRequest.getEmail())
                .withGender(signupRequest.getGender())
                .withPassword(pswdEncoder.encode(signupRequest.getPassword()))
                .withCustomer(customer)
                .withAuthRole(signupRequest.getAuthRole()).build();
    }

    @Transactional
    public void saveUser(UserPojo userPojo) {
        lg.debug("Save user: {}", userPojo);
        try {
            User userEntity = userPojo.toEntity();
//            Customer customerEntity = userEntity.getCustomer();
//            if(customerEntity != null) {
//                var orders = customerEntity.getOrders();
//                var addresses = customerEntity.getAddresses();
//                if(orders != null) orders.forEach(order -> order.setCustomer(customerEntity));
//                if(addresses != null && !addresses.isEmpty()) addressRepository.saveAll(addresses);
//            }
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

    @Transactional
    public UserPojo saveUser(SignupRequest signupRequest) {
        var user = createUser(signupRequest);
        saveUser(user);
        return user;
    }

    @Transactional
    public UserPojo update(@Valid SignupRequest signupRequest, Principal oldPrincipal) {
        UserPojo newUserPojo = createUser(signupRequest);
        UserPojo oldUserPojo = getUserByPrincipal(oldPrincipal, false);
        newUserPojo.setId(oldUserPojo.getId());
        newUserPojo.setCreated(oldUserPojo.getCreated());
        if(newUserPojo.getCustomer() != null && oldUserPojo.getCustomer() != null) {
            newUserPojo.getCustomer().setId(oldUserPojo.getCustomer().getId());
        }
        if(newUserPojo.getCustomer() == null && oldUserPojo.getCustomer() != null) {
            userRepository.deleteById(oldUserPojo.getId());
        }
        saveUser(newUserPojo);
        return newUserPojo;
    }

    @Transactional
    public UserPojo update(UserPojo newUser, Principal oldUserPrincipal) {
        UserPojo oldUser = getUserByPrincipal(oldUserPrincipal, false);
        newUser.setId(oldUser.getId());
        newUser.setCreated(oldUser.getCreated());
        saveUser(newUser);
        return newUser;
    }

    private UserPojo getUserPojo(User user, boolean lazyMode) {
        if(!lazyMode && user.getCustomer() != null) {
            Hibernate.initialize(user.getCustomer().getAddresses());
            if(user.getCustomer().getOrders() != null) Hibernate.initialize(user.getCustomer().getOrders());
        }
        return UserPojo.ofEntity(user);
    }

    public UserPojo getUserByPrincipal(Principal principal, boolean lazyMode) {
       User user = userRepository.getUserByEmail(principal.getName())
                .orElseThrow(() ->
                        new EntityPersistenceException("Unable find user with name: " + principal.getName())
                );
       return getUserPojo(user, lazyMode);
    }

    public long getUserIdByCustomerId(long customerId) {
        return userRepository.getUserIdByCustomerId(customerId);
    }

    public long getUserIdByPrincipal(Principal principal) {
        return userRepository.getUserIdByEmail(principal.getName());
    }

    public UserPojo getUserById(long userId, boolean lazyMode) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityPersistenceException("User with id[%d] not found".formatted(userId))
        );
        return getUserPojo(user, lazyMode);
    }
}
