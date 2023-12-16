package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.Order;
import ru.ssau.webcaffe.entity.User;
import java.time.LocalDateTime;
import java.util.*;

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

    @Query("select c.id from Customer c where c.user.id = :userId")
    long getCustomerIdByUserId(long userId);

    @Transactional
    @Modifying
    @Query("update Customer c set c.orders = :orders where c.id = :id")
    void updateOrders(long id, Set<Order> orders);

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

    @Query("from Customer where id in :ids")
    Set<Customer> getCustomers(Set<Long> ids);

    @Transactional
    @Modifying
    @Query("delete from Customer c where c.id = :id")
    void deleteById(long id);

    @Transactional
    @Modifying
    @Query("delete from Customer c where c.user.id = :userId")
    void deleteByUserId(long userId);

}