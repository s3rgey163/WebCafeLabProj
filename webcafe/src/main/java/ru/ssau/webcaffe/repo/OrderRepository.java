package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.webcaffe.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}