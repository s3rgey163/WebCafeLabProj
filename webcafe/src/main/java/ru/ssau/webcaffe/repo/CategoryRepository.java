package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> getCategoryByName(String name);

    @Query("from Category c left join fetch c.products where c.name = :name")
    Optional<Category> getCategoryByNameEager(String name);

    Optional<Category> getCategoryById(long id);
    @Query("from Category c left join fetch c.products where c.id = :id")
    Optional<Category> getCategoryByIdEager(long id);

    @Modifying
    @Query("delete Category c where c.name = :name")
    void deleteCategoryByName(String name);

    @Transactional
    @Modifying
    @Query("update Category set name = :name where id = :id")
    void updateCategoryNameById(long id, String name);
}