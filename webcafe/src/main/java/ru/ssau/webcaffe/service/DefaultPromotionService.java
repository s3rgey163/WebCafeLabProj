package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.pojo.PromotionPojo;
import ru.ssau.webcaffe.repo.PromotionRepository;
import ru.ssau.webcaffe.util.Util;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class DefaultPromotionService {
    private PromotionRepository promotionRepository;

    public DefaultPromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<PromotionPojo> getAvailablePromosFor(ProductType type) {
         var promos = promotionRepository.getAvailablePromosFor(type);
         return Util.mapCollection(promos, PromotionPojo::ofEntity, ArrayList::new);
    }
}
