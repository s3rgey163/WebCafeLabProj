package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.entity.Promotion;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query("from Promotion p where :type in (p.productTypes)")
    List<Promotion> getAvailablePromosFor(ProductType type);


}