package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.webcaffe.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}