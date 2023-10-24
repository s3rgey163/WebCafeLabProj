package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}