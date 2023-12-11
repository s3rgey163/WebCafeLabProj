package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.exception.EntityPersistenceException;
import ru.ssau.webcaffe.pojo.CategoryPojo;
import ru.ssau.webcaffe.repo.CategoryRepository;

@Service
@Primary
public class DefaultCategoryService {
    private CategoryRepository categoryRepository;

    public DefaultCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryPojo getCategoryByName(String name, boolean lazyMode) {
         var categoryOptional = lazyMode
                ? categoryRepository.getCategoryByName(name)
                : categoryRepository.getCategoryByNameEager(name);
         return CategoryPojo.ofEntity(categoryOptional.orElseThrow(() ->
                 new EntityPersistenceException("Category with name[%s] not found"
                         .formatted(name))
         ));
    }

    public CategoryPojo getCategoryById(long id, boolean lazyMode) {
        Category category = (lazyMode
                ? categoryRepository.getCategoryById(id)
                : categoryRepository.getCategoryByIdEager(id)).orElseThrow(
                () -> new EntityPersistenceException("Category with id[%d] not found"
                        .formatted(id))
        );
        return CategoryPojo.ofEntity(category);
    }

    public void save(CategoryPojo categoryPojo) {
        Category category = categoryPojo.toEntity();
        category.getProducts().forEach(product -> {
            product.setCategory(category);
            product.getTypes().forEach(productType ->
                    productType.setProduct(product)
            );
        });
        categoryRepository.save(category);
    }

    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }

    public void deleteByName(String name) {
        categoryRepository.deleteCategoryByName(name);
    }

    public void updateNameById(long categoryId, String name) {
        categoryRepository.updateCategoryNameById(categoryId, name);
    }
}
