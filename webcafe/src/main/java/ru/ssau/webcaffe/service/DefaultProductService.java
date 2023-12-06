package ru.ssau.webcaffe.service;

import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.pojo.ProductPojo;
import ru.ssau.webcaffe.repo.ProductRepository;
import ru.ssau.webcaffe.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DefaultProductService {
    private ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductPojo> getByCategory(Category category, boolean lazyMode) {
        List<Product> products = lazyMode
                ? productRepository.getByCategory(category)
                : productRepository.getByCategoryEager(category);
        return Util.mapCollection(products, ProductPojo::ofEntity, ArrayList::new);
    }

    public List<ProductPojo> getByCategoryId(long categoryId, boolean lazyMode) {
        List<Product> products = lazyMode
                ? productRepository.getByCategoryId(categoryId)
                : productRepository.getByCategoryIdEager(categoryId);
        return Util.mapCollection(products, ProductPojo::ofEntity, ArrayList::new);
    }

}
