package ru.ssau.webcaffe.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.util.Util;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * DTO for {@link Product}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductPojo {
    private long id;

    private String name;

    private Set<ProductTypePojo> types;

    public static ProductPojo ofEntity(Product product) {
        return new ProductPojo(
                product.getId(),
                product.getName(),
                product.getTypes() == null
                        ? null
                        : Util.mapPersistenceCollection(
                        product.getTypes(),
                        ProductTypePojo::ofEntity,
                        HashSet::new
                )
        );
    }


    public Product toEntity() {
        return new Product(
                id,
                name,
                null,
                types == null
                        ? null
                        : Util.mapPersistenceCollection(
                        types,
                        ProductTypePojo::toEntity,
                        HashSet::new
                )
        );
    }
}
