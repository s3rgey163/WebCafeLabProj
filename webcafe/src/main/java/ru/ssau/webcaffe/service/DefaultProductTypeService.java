package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.pojo.ProductTypePojo;

@Primary
@Service
public class DefaultProductTypeService implements ProductService {
    @Override
    public ProductTypePojo getProductType(long id) {
        return null;
    }
}
