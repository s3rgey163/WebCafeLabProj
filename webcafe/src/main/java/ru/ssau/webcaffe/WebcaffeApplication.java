package ru.ssau.webcaffe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.webcaffe.entity.Weight;
import ru.ssau.webcaffe.entity.WeightType;
import ru.ssau.webcaffe.pojo.*;
import ru.ssau.webcaffe.repo.AddressRepository;
import ru.ssau.webcaffe.repo.CustomerRepository;
import ru.ssau.webcaffe.service.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;


@SpringBootApplication
public class WebcaffeApplication implements CommandLineRunner {

    private final AddressService addressService;
    private final DefaultCustomerService customerService;

    private final DefaultOrderService orderService;

    private final DefaultProductService productService;

    private final DefaultProductTypeService productTypeService;

    private final DefaultPromotionService promotionService;

    private final DefaultCategoryService categoryService;

    private final UserService userService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public WebcaffeApplication(
            AddressService addressService,
            DefaultCustomerService customerService,
            DefaultOrderService orderService,
            DefaultProductService productService,
            DefaultProductTypeService productTypeService,
            DefaultPromotionService promotionService,
            DefaultCategoryService categoryService,
            UserService userService,
            AddressRepository addressRepository,
            CustomerRepository customerRepository) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.promotionService = promotionService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
//        SpringApplication.run(WebcaffeApplication.class, args);
        SpringApplication.run(WebcaffeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AddressPojo addr = new AddressPojo(0, "Самара", "ул. Свободы, д. 16", 8);
        OrderPojo order = new OrderPojo(
                0,
                LocalDateTime.now(),
                null,
                "Побыстрее",
                Collections.emptySet()
        );
        var principal = new Principal() {
            @Override
            public String getName() {
                return "sergeyknyz75@gmail.com";
            }
        };
//        customerService.updateOrders(1, List.of(
//                        new OrderPojo(0, LocalDateTime.now(), null, "Перемога", Collections.emptySet()),
//                        new OrderPojo(1, LocalDateTime.now(), null, "Зрада", Collections.emptySet())
//                )
//            );
        var productTypes = Set.of(
                new ProductTypePojo(0, new Weight(1, WeightType.KG), new BigDecimal(12.4), "AA"),
                new ProductTypePojo(1, new Weight(1, WeightType.KG), new BigDecimal(12.4), "AA"));
        categoryService.save(new CategoryPojo(
                0,
                "Второе",
                "Блюдо второе",
                Set.of(new ProductPojo(0, "AA", productTypes))
        ));
//        customerService.save(2, CustomerPojo.builder()
//                .addressPojos(Set.of(new AddressPojo(0, "Самара", "Ново-Салова", 12  )))
//                .name("Марат")
//                .secondName("Князьков")
//                .middleName("Сергеевич")
//                .birthday(LocalDateTime.now())
//                .build());


//        var userAddresses = addressService.getByUserId(2);
//        addressService.saveAddress(new Principal() {
//            @Override
//            public String getName() {
//                return "sergeyknyz75@gmail.com";
//            }
//        }, addr);
//        customerService.saveCustomer(53, CustomerPojo.builder()
//                .name("Александр")
//                .secondName("Иванов")
//                .middleName("Иванович")
//                .birthday(LocalDateTime.now())
//                .addressPojos(Set.of(new AddressPojo(0, "Samara", "GG", 54)))
//                .build()
//        );
    }
}