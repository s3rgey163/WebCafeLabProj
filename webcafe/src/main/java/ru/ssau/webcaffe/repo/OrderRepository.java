package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.Order;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    String FIND_BY_DATA_OF_MONTH_QUERY = "from Order o " +
            "where year(cast(o.dateTime as timestamp)) = :year" +
            " and month(cast(o.dateTime as timestamp)) = :month" +
            " and day(cast(o.dateTime as timestamp)) = :day";
    String FIND_BY_DAY_OF_MONTH_ASC_QUERY = FIND_BY_DATA_OF_MONTH_QUERY
            + " order by o.dateTime";
    String FIND_BY_DAY_OF_MONTH_DESC_QUERY = FIND_BY_DATA_OF_MONTH_QUERY
            + " order by o.dateTime desc";
    default List<Order> getByCustomerAndDateTimeBetweenOrderByDateTime(
            Customer customer,
            LocalDateTime when,
            LocalDateTime until
    ) {
        return getByCustomerIdAndDateTimeBetweenOrderByDateTime(
                customer.getId(),
                when,
                until
        );
    }
    List<Order> getByCustomerIdAndDateTimeBetweenOrderByDateTime(
            long customerId,
            LocalDateTime when,
            LocalDateTime until
    );
    default List<Order> getByCustomerAndDateTimeBetweenOrderByDateTimeDesc(
            Customer customer,
            LocalDateTime when,
            LocalDateTime until
    ) {
        return getByCustomerIdAndDateTimeBetweenOrderByDateTimeDesc(
                customer.getId(),
                when,
                until
        );
    }
    List<Order> getByCustomerIdAndDateTimeBetweenOrderByDateTimeDesc(
            long customerId,
            LocalDateTime when,
            LocalDateTime until
    );
    default List<Order> getByCustomerOrderByDateTime(Customer customer) {
        return getByCustomerIdOrderByDateTime(customer.getId());
    }
    List<Order> getByCustomerIdOrderByDateTime(long id);
    @Query(FIND_BY_DAY_OF_MONTH_ASC_QUERY)
    List<Order> getByDateOrderByDate(int year, int month, int day);
    @Query(FIND_BY_DAY_OF_MONTH_DESC_QUERY)
    List<Order> getByDateOrderByDateDesc(int year, int month, int day);
    List<Order> getByDateTimeBetweenOrderByDateTime(LocalDateTime when, LocalDateTime until);
    List<Order> getByDateTimeBetweenOrderByDateTimeDesc(LocalDateTime when, LocalDateTime until);

    default List<Order> getByCustomerOrderByDateTimeDesc(Customer customer) {
        return getByCustomerIdOrderByDateTimeDesc(customer.getId());
    }

    List<Order> getByCustomerIdOrderByDateTimeDesc(long customerId);

    void deleteByIdAndCustomerId(long id, long customerId);

    @Query("delete from Order o where o.id = :orderId and o.customer.user.id = :userId")
    void deleteByIdAndUserId(long orderId, long userId);
}