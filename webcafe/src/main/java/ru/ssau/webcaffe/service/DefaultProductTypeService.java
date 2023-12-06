package ru.ssau.webcaffe.service;

import ru.ssau.webcaffe.repo.ProductTypeRepository;

public class DefaultProductTypeService {
    private ProductTypeRepository productTypeRepository;

    public DefaultProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }



}
