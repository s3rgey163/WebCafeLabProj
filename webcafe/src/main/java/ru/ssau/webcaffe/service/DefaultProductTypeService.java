package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.entity.Weight;
import ru.ssau.webcaffe.exception.EntityPersistenceException;
import ru.ssau.webcaffe.pojo.ProductTypePojo;
import ru.ssau.webcaffe.repo.ProductRepository;
import ru.ssau.webcaffe.repo.ProductTypeRepository;
import ru.ssau.webcaffe.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Primary
public class DefaultProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    private final ProductRepository productRepository;

    public DefaultProductTypeService(
            ProductTypeRepository productTypeRepository,
            ProductRepository productRepository
    ) {
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
    }

    public ProductTypePojo getById(long id) {
        ProductType productType = productTypeRepository.findById(id).orElseThrow(() ->
                new EntityPersistenceException("Product type with id[%d] not found"
                        .formatted(id))
        );
        return ProductTypePojo.ofEntity(productType);
    }

    public List<ProductTypePojo> getByProduct(Product product) {
        List<ProductType> productTypes = productTypeRepository.getByProduct(product);
        return Util.mapCollection(productTypes, ProductTypePojo::ofEntity, ArrayList::new);
    }

    public List<ProductTypePojo> getByProductId(long productId) {
        List<ProductType> productTypes = productTypeRepository.getByProductId(productId);
        return Util.mapCollection(productTypes,  ProductTypePojo::ofEntity, ArrayList::new);
    }

    private void save(Product product, ProductTypePojo typePojo) {
        Objects.requireNonNull(product);
        product.setTypes(new ArrayList<>(List.of(typePojo.toEntity(product))));
        productRepository.save(product);
    }

    public void save(String productName, ProductTypePojo typePojo) {
        Product product = productRepository.getByName(productName).orElseThrow(() ->
                new EntityPersistenceException("Product type with name[%s] not found"
                        .formatted(productName))
        );
        save(product, typePojo);
    }

    public void update(long productTypeId, ProductTypePojo productTypePojo) {
        ProductType productType = productTypeRepository.findById(productTypeId).orElseThrow(() ->
                new EntityPersistenceException("Product type with id[%d] not found"
                        .formatted(productTypeId))
        );
        ProductType newProductType = productTypePojo.toEntity(productType.getProduct());
        newProductType.setId(productTypeId);
        productTypeRepository.save(newProductType);
    }

    public void save(long productId, ProductTypePojo typePojo) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityPersistenceException("Product with id[%d] not found".formatted(productId))
        );
        save(product, typePojo);
    }

    public void createProductType(String productName, Weight weight, BigDecimal price, String describe) {
        ProductTypePojo typePojo = new ProductTypePojo(0,weight,price,describe);
        save(productName, typePojo);
    }

    public void createProductType(long productId, Weight weight, BigDecimal price, String describe) {
        ProductTypePojo productTypePojo = new ProductTypePojo(
                0,
                weight,
                price,
                describe
        );
        save(productId, productTypePojo);
    }

    public void deleteById(long typeId) {
        productTypeRepository.deleteById(typeId);
    }

    public void deleteAllByProductId(long productId) {
        productTypeRepository.deleteAllByProductId(productId);
    }
}
