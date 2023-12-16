package ru.ssau.webcaffe.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ssau.webcaffe.payload.responce.MessageResponse;
import ru.ssau.webcaffe.pojo.ProductPojo;
import ru.ssau.webcaffe.service.DefaultProductService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/product")
@PreAuthorize("permitAll()")
public class ProductController {
    private final DefaultProductService productService;

    public ProductController(DefaultProductService productService) {
        this.productService = productService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("category/{categoryId}")
    public List<ProductPojo> getProductsByCategoryId(@PathVariable long categoryId) {
        return productService.getByCategoryId(categoryId, true);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public ProductPojo getProductById(@PathVariable long id) {
        return productService.getById(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/category/{categoryId}/create")
    public MessageResponse createProduct(@PathVariable long categoryId, @RequestBody ProductPojo productPojo) {
        productService.save(categoryId, productPojo);
        return new MessageResponse("Product[%s] successfully created".formatted(productPojo));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{id}/update")
    public MessageResponse updateProduct(@PathVariable long id, @RequestBody ProductPojo productPojo) {
        productService.update(id, productPojo);
        return new MessageResponse("Product '%s' successfully created".formatted(productPojo));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{id}/delete")
    public MessageResponse deleteProductById(@PathVariable long id) {
        productService.deleteById(id);
        return new MessageResponse("Product with id[%d] successfully deleted".formatted(id));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("category/{categoryId}/delete")
    public MessageResponse deleteProductsByCategoryId(@PathVariable long categoryId) {
        productService.deleteAllByCategoryId(categoryId);
        return new MessageResponse("All products in category with id[%d] successfully deleted"
                .formatted(categoryId)
        );
    }


}
