package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.webcaffe.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}