package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.entity.ProductType;

@Primary
@Service
public class DefaultProductTypeService implements ProductService{
    @Override
    public ProductType getProductType(long id) {
        return null;
    }
}
