package ru.ssau.webcaffe.service;

import ru.ssau.webcaffe.repo.ProductTypeRepository;

public class ProductTypeService {
    private ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }
}
