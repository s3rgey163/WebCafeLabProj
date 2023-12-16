package ru.ssau.webcaffe.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ssau.webcaffe.pojo.AddressPojo;
import ru.ssau.webcaffe.pojo.CategoryPojo;
import ru.ssau.webcaffe.pojo.ProductPojo;
import ru.ssau.webcaffe.service.DefaultCategoryService;
import ru.ssau.webcaffe.service.DefaultProductService;

import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
@PreAuthorize("permitAll()")
public class CategoryController {
    private final DefaultCategoryService categoryService;

    private final DefaultProductService productService;

    public CategoryController(
            DefaultCategoryService categoryService,
            DefaultProductService productService
    ) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public List<CategoryPojo> getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("{id}")
    public CategoryPojo getCategory(@PathVariable long id) {
        return categoryService.getCategoryById(id, true);
    }

    @PostMapping("create")
    public void createCategory(@RequestBody CategoryPojo categoryPojo) {
        categoryService.save(categoryPojo);
    }

    @PostMapping("{categoryId}/products")
    public void createProduct(@PathVariable long categoryId, @RequestBody ProductPojo productPojo) {
        productService.save(categoryId, productPojo);
    }

    @GetMapping("{categoryId}/products")
    public List<ProductPojo> getAllProducts(@PathVariable long categoryId) {
        return productService.getByCategoryId(categoryId, true);
    }

}
