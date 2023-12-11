package ru.ssau.webcaffe.pojo;

import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Promotion;
import ru.ssau.webcaffe.util.Util;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link Promotion}
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data public class PromotionPojo {
    private long id;

    private String code;

    private int validDays;

    private int minPrice;

    private int discount;

    @ManyToMany
    private Set<ProductTypePojo> productTypePojos;

    @ManyToMany
    private Set<ProductTypePojo> conditionProductTypePojos;

    public static PromotionPojo ofEntity(Promotion promotion) {
        var productTypePojos = promotion.getProductTypes() == null
                ? null
                : Util.mapCollection(
                    promotion.getProductTypes(),
                    ProductTypePojo::ofEntity,
                    HashSet::new
                );
        var conditionProductTypePojos = promotion.getConditionProductTypes() == null
                ? null
                : Util.mapPersistenceCollection(
                    promotion.getConditionProductTypes(),
                    ProductTypePojo::ofEntity,
                    HashSet::new
                );
        return PromotionPojo.builder()
                .id(promotion.getId())
                .code(promotion.getCode())
                .validDays(promotion.getValidDays())
                .minPrice(promotion.getMinPrice())
                .discount(promotion.getDiscount())
                .productTypePojos(productTypePojos)
                .conditionProductTypePojos(conditionProductTypePojos)
                .build();
    }

    public Promotion toEntity() {
        var productTypes = productTypePojos == null
                ? null
                : Util.mapPersistenceCollection(
                productTypePojos,
                ProductTypePojo::toEntity,
                HashSet::new
        );
        var conditionProductType = conditionProductTypePojos == null
                ? null
                : Util.mapPersistenceCollection(
                conditionProductTypePojos,
                ProductTypePojo::toEntity,
                HashSet::new
        );
        return new Promotion(
                id,
                code,
                validDays,
                minPrice,
                discount,
                productTypes,
                conditionProductType
        );
    }
}
