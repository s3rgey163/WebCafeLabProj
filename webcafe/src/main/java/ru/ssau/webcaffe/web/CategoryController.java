package ru.ssau.webcaffe.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ssau.webcaffe.payload.responce.MessageResponse;
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


    public CategoryController(
            DefaultCategoryService categoryService
    ) {
        this.categoryService = categoryService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CategoryPojo> getCategories() {
        return categoryService.getAllCategories();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public CategoryPojo getCategory(@PathVariable long id) {
        return categoryService.getCategoryById(id, true);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("create")
    public MessageResponse createCategory(@RequestBody CategoryPojo categoryPojo) {
        categoryService.save(categoryPojo);
        return new MessageResponse("Category %s successfully created".formatted(categoryPojo.getName()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{id}/delete")
    public MessageResponse deleteCategoryById(@PathVariable long id) {
        categoryService.deleteById(id);
        return new MessageResponse("Category with id[%s] successfully deleted".formatted(id));
    }
}
