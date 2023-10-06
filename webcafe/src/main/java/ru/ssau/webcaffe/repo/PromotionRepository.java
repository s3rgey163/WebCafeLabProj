package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.webcaffe.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}