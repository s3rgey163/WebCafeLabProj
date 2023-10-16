package ru.ssau.webcaffe.service;

import ru.ssau.webcaffe.entity.ProductType;
import ru.ssau.webcaffe.pojo.ProductTypePojo;

public interface ProductService {
    ProductTypePojo getProductType(long id);
}
