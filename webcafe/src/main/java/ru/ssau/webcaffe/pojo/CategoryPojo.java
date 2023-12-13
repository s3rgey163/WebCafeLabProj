package ru.ssau.webcaffe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link Category}
 */
@AllArgsConstructor
@Data
public class CategoryPojo {
    private long id;

    private String name;

    private String describe;

    private Set<ProductPojo> productPojos;

    public static CategoryPojo ofEntity(Category category) {
        return new CategoryPojo(
                category.getId(),
                category.getName(),
                category.getDescribe(),
                category.getProducts() == null
                        ? null
                        : Util.mapPersistenceCollection(
                        category.getProducts(),
                        ProductPojo::ofEntity,
                        HashSet::new
                )
        );
    }

    public Category toEntity() {
        return new Category(
                id,
                name,
                describe,
                productPojos == null
                        ? null
                        : Util.mapPersistenceCollection(
                        productPojos,
                        ProductPojo::toEntity,
                        ArrayList::new
                )
        );
    }
}
