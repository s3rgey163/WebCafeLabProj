package ru.ssau.webcaffe.service;

import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.repo.ProductTypeRepository;

public class ProductTypeService implements Service<ProductType, Long> {
    private ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }
}
