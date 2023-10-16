package ru.ssau.webcaffe.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.entity.ProductType;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data public class ProductPojo {
    private long id;

    private String name;

    private Set<ProductTypePojo> types;

    public static ProductPojo ofEntity(Product product) {
        return new ProductPojo(
                product.getId(),
                product.getName(),
                product.getTypes().stream()
                        .map(ProductTypePojo::ofEntity)
                        .collect(Collectors.toSet())
        );
    }


    public Product toEntity() {
        return new Product(
                id,
                name,
                null,
                types.stream()
                        .map(ProductTypePojo::toEntity)
                        .collect(Collectors.toSet())
        );
    }
}
