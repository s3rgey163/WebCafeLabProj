package ru.ssau.webcaffe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Product;

import java.util.List;

@AllArgsConstructor
@Data public class CategoryPojo {
    private long id;

    private String name;

    private String describe;

    private List<Product> products;

    public Category toEntity() {
        return new Category(
                id,
                name,
                describe,
                products
        );
    }

    public static CategoryPojo ofEntity(Category category) {
        return new CategoryPojo(
                category.getId(),
                category.getName(),
                category.getDescribe(),
                category.getProducts()
        );
    }
}
