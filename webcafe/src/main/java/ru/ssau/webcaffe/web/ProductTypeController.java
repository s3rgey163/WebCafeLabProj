package ru.ssau.webcaffe.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ssau.webcaffe.payload.responce.MessageResponse;
import ru.ssau.webcaffe.pojo.ProductTypePojo;
import ru.ssau.webcaffe.service.DefaultProductTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/producttype")
public class ProductTypeController {
    private final DefaultProductTypeService productTypeService;

    public ProductTypeController(DefaultProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public ProductTypePojo getProductTypeById(@PathVariable long id) {
        return productTypeService.getById(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("product/{productId}")
    public List<ProductTypePojo> getAllProductTypesByProductId(@PathVariable long productId) {
        return productTypeService.getByProductId(productId);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("product/{productId}/create")
    public MessageResponse createProductType(
            @PathVariable long productId,
            @RequestBody ProductTypePojo productTypePojo
    ) {
        productTypeService.save(productId, productTypePojo);
        return new MessageResponse("Product type [%s] successfully created"
                .formatted(productTypePojo)
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{id}/update")
    public MessageResponse updateProductType(
            @PathVariable long id,
            @RequestBody ProductTypePojo productTypePojo
    ) {
        productTypeService.update(id, productTypePojo);
        return new MessageResponse("Product type [%s] successfully".formatted(productTypePojo));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{id}/delete")
    public MessageResponse deleteProductTypeById(@PathVariable long id) {
        productTypeService.deleteById(id);
        return new MessageResponse("Product type with id [%d] successfully deleted"
                .formatted(id)
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("product/{productId}/delete")
    public MessageResponse deleteAllProductTypesByProductId(@PathVariable long productId) {
        productTypeService.deleteAllByProductId(productId);
        return new MessageResponse("Product types with product id [%d] successfully deleted"
                .formatted(productId)
        );
    }
}
