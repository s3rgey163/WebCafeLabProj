package ru.ssau.webcaffe.pojo;

import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.entity.Weight;

import java.math.BigDecimal;

@AllArgsConstructor
public class ProductTypePojo {
    private Weight weight;

    private BigDecimal price;

    private String describe;

    public static ProductTypePojo ofEntity(ProductType productType) {
        return new ProductTypePojo(
                productType.getWeight(),
                productType.getPrice(),
                productType.getDescribe()
        );
    }
}
