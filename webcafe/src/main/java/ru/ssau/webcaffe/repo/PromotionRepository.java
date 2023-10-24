package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}