package ru.ssau.webcaffe.service;

import ru.ssau.webcaffe.entity.Product;
import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.pojo.ProductTypePojo;
import ru.ssau.webcaffe.repo.ProductTypeRepository;
import ru.ssau.webcaffe.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DefaultProductTypeService {
    private ProductTypeRepository productTypeRepository;

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
}
