package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.User;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByLogin(String login);

    Optional<User> getUserByEmail(String email);

    @Query("from User u join u.authRole a where a = :authRole")

    List<User> getByAuthRole(User.AuthRole authRole);
}
