package ru.ssau.webcaffe.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ssau.webcaffe.service.DefaultProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@PreAuthorize("permitAll()")
public class ProductController {
    private final DefaultProductService productService;

    public ProductController(DefaultProductService productService) {
        this.productService = productService;
    }
}
