package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.webcaffe.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}