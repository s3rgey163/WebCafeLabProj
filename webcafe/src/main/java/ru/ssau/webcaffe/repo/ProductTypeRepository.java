package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.webcaffe.entity.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    ProductType getProductTypeByProductId(long id);
}