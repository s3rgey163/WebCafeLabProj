package ru.ssau.webcaffe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.util.Util;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class CategoryPojo {
    private long id;

    private String name;

    private String describe;

    private Set<ProductPojo> productPojos;

    public Category toEntity() {
        return new Category(
                id,
                name,
                describe,
                productPojos == null
                        ? null
                        : Util.collectionMapper(
                        productPojos,
                        ProductPojo::toEntity,
                        HashSet::new
                )
        );
    }

    public static CategoryPojo ofEntity(Category category) {
        return new CategoryPojo(
                category.getId(),
                category.getName(),
                category.getDescribe(),
                category.getProducts() == null
                        ? null
                        : Util.collectionMapper(
                        category.getProducts(),
                        ProductPojo::ofEntity,
                        HashSet::new
                )
        );
    }
}
