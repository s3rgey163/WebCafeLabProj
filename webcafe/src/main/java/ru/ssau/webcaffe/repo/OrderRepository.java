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
            "where year(cast(o.date as timestamp)) = :year" +
            " and month(cast(o.date as timestamp)) = :month" +
            " and day(cast(o.date as timestamp)) = :day";
    String FIND_BY_DAY_OF_MONTH_ASC_QUERY = FIND_BY_DATA_OF_MONTH_QUERY
            + " order by o.date";
    String FIND_BY_DAY_OF_MONTH_DESC_QUERY = FIND_BY_DATA_OF_MONTH_QUERY
            + " order by o.date desc";
    List<Order> getByCustomerAndDateTimeBetweenOrderByDateTime(
            Customer customer,
            LocalDateTime when,
            LocalDateTime until
    );
    List<Order> getByCustomerIdAndDateTimeBetweenOrderByDateTime(
            long customerId,
            LocalDateTime when,
            LocalDateTime until
    );
    List<Order> getByCustomerAndDateTimeBetweenOrderByDateTimeDesc(
            Customer customer,
            LocalDateTime when,
            LocalDateTime until
    );
    List<Order> getByCustomerIdAndDateTimeBetweenOrderByDateTimeDesc(
            long customerId,
            LocalDateTime when,
            LocalDateTime until
    );
    List<Order> getByCustomerOrderByDateTime(Customer customer);
    List<Order> getByCustomerIdOrderByDateTime(long id);
    @Query(FIND_BY_DAY_OF_MONTH_ASC_QUERY)
    List<Order> getByDateOrderByDate(int year, int month, int day);
    @Query(FIND_BY_DAY_OF_MONTH_DESC_QUERY)
    List<Order> getByDateOrderByDateDesc(int year, int month, int day);
    List<Order> getByDateTimeBetweenOrderByDateTime(LocalDateTime when, LocalDateTime until);
    List<Order> getByDateTimeBetweenOrderByDateTimeDesc(LocalDateTime when, LocalDateTime until);
    List<Order> getByCustomerIdOrderByDateTimeDesc(long customerId);
    List<Order> getByCustomerOrderByDateTimeDesc(Customer customer);
}