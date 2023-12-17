package ru.ssau.webcaffe.pojo;

import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.entity.Weight;

import java.math.BigDecimal;

/**
 * DTO for {@link ProductType}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductTypePojo {
    private long id;

    private Weight weight;

    private BigDecimal price;

    private String describe;

    public static ProductTypePojo ofEntity(ProductType productType) {
        return new ProductTypePojo(
                productType.getId(),
                productType.getWeight(),
                productType.getPrice(),
                productType.getDescribe()
        );
    }

    public ProductType toEntity(Product product) {
        return new ProductType(id, weight, product, price, describe);
    }

    public ProductType toEntity() {
        return toEntity(null);
    }

    @Override
    public String toString() {
        return "ProductTypePojo{" +
                "id=" + id +
                ", weight=" + weight +
                ", price=" + price +
                ", describe='" + describe + '\'' +
                '}';
    }
}
