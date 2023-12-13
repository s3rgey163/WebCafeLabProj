package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.exception.EntityPersistenceException;
import ru.ssau.webcaffe.pojo.ProductPojo;
import ru.ssau.webcaffe.pojo.ProductTypePojo;
import ru.ssau.webcaffe.repo.CategoryRepository;
import ru.ssau.webcaffe.repo.CustomerRepository;
import ru.ssau.webcaffe.repo.ProductRepository;
import ru.ssau.webcaffe.util.Util;

import java.util.*;
import java.util.function.Function;

@Service
@Primary
public class DefaultProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public DefaultProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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

    public void createProduct(long categoryId, String name) {
        ProductPojo productPojo = new ProductPojo(0, name, Collections.emptyList());
        save(categoryId, productPojo);
    }

    public void createProduct(String categoryName, String productName) {
        ProductPojo productPojo = new ProductPojo(0, productName, Collections.emptyList());
        save(categoryName, productPojo);
    }


    public void createProduct(long categoryId, String name, List<ProductTypePojo> typePojos) {
        ProductPojo productPojo = new ProductPojo(0, name, typePojos);
        save(categoryId, productPojo);
    }

    public void createProduct(String categoryName, String productName, List<ProductTypePojo> typePojos) {
        ProductPojo productPojo = new ProductPojo(0, productName, typePojos);
        save(categoryName, productPojo);
    }

    private void save(Category category,  ProductPojo... productPojos) {
        Objects.requireNonNull(category);
        List<Product> products = Util.mapCollection(pj -> pj.toEntity(category), ArrayList::new, productPojos);
        category.setProducts(products);
        categoryRepository.save(category);
    }

    public void save(String categoryName, ProductPojo... productPojos) {
        if (productPojos.length == 0) return;
        Category category = categoryRepository.getCategoryByName(categoryName).orElseThrow(() ->
                new EntityPersistenceException("Category with name[%s] not found".formatted(categoryName))
        );
        save(category, productPojos);
    }

    public void save(long categoryId, ProductPojo... productPojos) {
        if (productPojos.length == 0) return;
        Category category = categoryRepository.getCategoryById(categoryId).orElseThrow(() ->
                new EntityPersistenceException("Category with id[%d] not found".formatted(categoryId)));
        save(category, productPojos);
    }

    public void updateName(long productId, String name) {
        productRepository.updateName(productId, name);
    }

    public void deleteById(long productId) {
        productRepository.deleteById(productId);
    }

    public void deleteByName(String name) {
        productRepository.deleteByName(name);
    }

    public void deleteAllByCategoryId(long categoryId) {
        productRepository.deleteAllByCategoryId(categoryId);
    }

    public void deleteAllByCategoryName(String categoryName) {
        deleteAllByCategoryName(categoryName);
    }
}
