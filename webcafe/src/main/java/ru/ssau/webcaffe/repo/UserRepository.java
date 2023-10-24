package ru.ssau.webcaffe.repo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ssau.webcaffe.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByLogin(String login);

    Optional<User> getUserByEmail(String email);

    List<User> getUsersByRole(Set<User.Role> role);

    default List<User> getUsersByRole(User.Role role) {
        return getUsersByRole(Set.of(role));
    }
}
