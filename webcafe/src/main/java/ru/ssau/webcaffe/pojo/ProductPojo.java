package ru.ssau.webcaffe.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.util.Util;

import java.util.*;
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

    private List<ProductTypePojo> types;

    public ProductPojo(long id, String name) {
        this(id, name, Collections.emptyList());
    }

    public static ProductPojo ofEntity(Product product) {
        return new ProductPojo(
                product.getId(),
                product.getName(),
                product.getTypes() == null
                        ? null
                        : Util.mapPersistenceCollection(
                        product.getTypes(),
                        ProductTypePojo::ofEntity,
                        ArrayList::new
                )
        );
    }


    public Product toEntity(Category category) {
        return new Product(
                id,
                name,
                category,
                types == null
                        ? null
                        : Util.mapCollection(
                                types,
                                ProductTypePojo::toEntity,
                                ArrayList::new
                        )

        );
    }

    public Product toEntity() {
        return toEntity(null);
    }
}
