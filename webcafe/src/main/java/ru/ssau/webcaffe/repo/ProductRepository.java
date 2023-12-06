package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getByCategory(Category category);
    List<Product> getByCategoryId(long id);

    @Query("from Product p join fetch p.types where p.category = :category")
    List<Product> getByCategoryEager(Category category);
    @Query("from Product p join fetch p.types where p.id = :id")
    List<Product> getByCategoryIdEager(long id);
}