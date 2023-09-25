package ru.ssau.webcafe;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.ssau.webcafe.entity.ProductCategory;

@Converter(autoApply = false)
public class CategoryConverter implements AttributeConverter<ProductCategory, String> {
    @Override
    public String convertToDatabaseColumn(ProductCategory attribute) {
        if(attribute == null) {
            throw new IllegalArgumentException("Attribute must be non null");
        }
        return attribute.getCategory();
    }

    @Override
    public ProductCategory convertToEntityAttribute(String dbData) {
        return ProductCategory.of(dbData);
    }
}
