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

@Service
@Primary
public class DefaultProductTypeService {
    private ProductTypeRepository productTypeRepository;

    private ProductRepository productRepository;

    public DefaultProductTypeService(
            ProductTypeRepository productTypeRepository,
            ProductRepository productRepository
    ) {
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
    }

    public DefaultProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public List<ProductTypePojo> getByProduct(Product product) {
        List<ProductType> productTypes = productTypeRepository.getByProduct(product);
        return Util.mapCollection(productTypes, ProductTypePojo::ofEntity, ArrayList::new);
    }

    public List<ProductTypePojo> getByProductId(long productId) {
        List<ProductType> productTypes = productTypeRepository.getByProductId(productId);
        return Util.mapCollection(productTypes,  ProductTypePojo::ofEntity, ArrayList::new);
    }

    public void createProductType(long productId, Weight weight, BigDecimal price, String describe) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityPersistenceException("Product with id[%d] not found".formatted(productId))
        );
        ProductType productType = new ProductType(
                0,
                weight,
                product,
                price,
                describe
        );
        product.setTypes(List.of(productType));
        productRepository.save(product);
    }
}
