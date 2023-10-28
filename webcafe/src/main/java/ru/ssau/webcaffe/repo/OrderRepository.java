package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.Order;

import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getByCustomerAndDateBetweenOrderByDate(
            Customer customer,
            Date start,
            Date end
    );
    List<Order> getByCustomerIdAndDateBetweenOrderByDate(
            long customerId,
            Date start,
            Date end
    );
    List<Order> getByCustomerAndDateBetweenOrderByDateDesc(
            Customer customer,
            Date start,
            Date end
    );
    List<Order> getByCustomerIdAndDateBetweenOrderByDateDesc(
            long customerId,
            Date start,
            Date end
    );
    List<Order> getByCustomerOrderByDate(Customer customer);
    List<Order> getByCustomerIdOrderByDate(long id);

    List<Order> getByDateOrderByDate(Date date);
    List<Order> getByDateOrderByDateDesc(Date date);
    List<Order> getByDateBetweenOrderByDate(Date start, Date end);
    List<Order> getByDateBetweenOrderByDateDesc(Date start, Date end);
    List<Order> getByCustomerIdOrderByDateDesc(long customerId);
    List<Order> getByCustomerOrderByDateDesc(Customer customer);
}