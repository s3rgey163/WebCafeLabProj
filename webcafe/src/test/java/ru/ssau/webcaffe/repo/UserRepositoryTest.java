package ru.ssau.webcaffe.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.ssau.webcaffe.WebcaffeApplication;
import ru.ssau.webcaffe.entity.User;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = WebcaffeApplication.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private User testUsr;

    @BeforeEach
    void setUp() {
        testUsr = User.builder().build();
        repository.save(testUsr);
    }

    @AfterEach
    void tearDown() {
        repository.delete(testUsr);
    }

    @Test
    void getUserByLogin() {
        User user = repository.getUserByLogin(testUsr.getLogin()).orElse(null);
        assertEquals(user, testUsr);
    }

    @Test
    void getUserByEmail() {
        User user = repository.getUserByEmail(testUsr.getEmail()).orElse(null);
        assertEquals(user, testUsr);
    }

    @Test
    void getUsersByAuthRole() {
    }
}