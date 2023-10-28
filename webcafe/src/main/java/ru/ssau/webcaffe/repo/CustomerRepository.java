package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.User;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("from Customer c join c.user where c.user.id = :userId")
    Optional<Customer> getCustomerByUserId(Long userId);
    @Query("from Customer where name = :firstName and secondName = :secondName and middleName = :middleName")
    Optional<Customer> getCustomerByFullName(String firstName, String secondName, String middleName);

    Optional<Customer> getCustomerByUser(User user);


}