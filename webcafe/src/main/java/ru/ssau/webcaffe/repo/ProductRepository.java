package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.entity.ProductType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> getByName(String name);

    @Query("from Product p left join p.types where p.name = ?1")
    Optional<Product> getByNameEager(String name);
    List<Product> getByCategory(Category category);
    List<Product> getByCategoryId(long id);

    @Query("from Product p join fetch p.types where p.category = :category")
    List<Product> getByCategoryEager(Category category);
    @Query("from Product p join fetch p.types where p.id = :id")
    List<Product> getByCategoryIdEager(long id);

    @Modifying
    @Query("update Product p set p.name = ?1 where p.id = ?2")
    void updateName(long id, String name);

    void deleteById(long id);

    void deleteByName(String name);

    @Transactional
    void deleteAllByCategoryId(long categoryId);
    @Transactional
    void deleteAllByCategoryName(String name);
}