package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.User;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByLogin(String login);

    Optional<User> getUserByEmail(String email);

    List<User> getUsersByAuthRole(User.AuthRole authRole);
}
