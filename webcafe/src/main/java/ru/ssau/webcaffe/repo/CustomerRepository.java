package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("from Customer c join c.user where c.user.id = :userId")
    Optional<Customer> getCustomerByUserId(Long userId);
    @Query("from Customer c " +
            "where c.name = :firstName " +
            "   and c.secondName = :secondName " +
            "   and c.middleName = :middleName")
    Optional<Customer> getCustomerByFullName(String firstName, String secondName, String middleName);

    Optional<Customer> getCustomerByUser(User user);

    @Transactional
    @Modifying
    @Query("update Customer c set c.name = :name, c.secondName = :secondName, c.middleName = :middleName, c.birthday = :birthday where c.id = :id")
    void updateFullNameAndBirthdayById(
            long id,
            String name,
            String secondName,
            String middleName,
            LocalDateTime birthday
    );
}