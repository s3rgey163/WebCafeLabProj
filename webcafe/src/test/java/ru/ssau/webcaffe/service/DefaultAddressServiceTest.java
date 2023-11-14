package ru.ssau.webcaffe.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import ru.ssau.webcaffe.WebcaffeApplication;
import ru.ssau.webcaffe.entity.User;
import ru.ssau.webcaffe.pojo.UserPojo;

import java.security.Principal;

@ContextConfiguration(classes = WebcaffeApplication.class)
public class DefaultAddressServiceTest {

    @Autowired
    private DefaultAddressService defaultAddressService;

    private Principal principal;

    @BeforeEach
    void setUp() {
        principal = new Principal() {
            @Override
            public String getName() {
                return "sergeyknyz75@gmail.com";
            }
        };
    }

    @Test
    void whenFindUserAddresses_thenUserAddressesAvailable() {
        System.out.println(defaultAddressService.getAddressesByPrincipal(principal));
    }
}
