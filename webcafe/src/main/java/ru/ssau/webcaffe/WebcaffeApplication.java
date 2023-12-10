package ru.ssau.webcaffe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.webcaffe.pojo.AddressPojo;
import ru.ssau.webcaffe.pojo.OrderPojo;
import ru.ssau.webcaffe.service.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;


@SpringBootApplication
public class WebcaffeApplication implements CommandLineRunner {

    private final AddressService addressService;
    private final DefaultCustomerService customerService;

    private final DefaultOrderService orderService;

    private final DefaultProductService productService;

    private final DefaultProductTypeService productTypeService;

    private final DefaultPromotionService promotionService;

    private final UserService userService;

    public WebcaffeApplication(
            AddressService addressService,
            DefaultCustomerService customerService,
            DefaultOrderService orderService,
            DefaultProductService productService,
            DefaultProductTypeService productTypeService,
            DefaultPromotionService promotionService,
            UserService userService
    ) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.promotionService = promotionService;
        this.userService = userService;
    }

    public static void main(String[] args) {
//        SpringApplication.run(WebcaffeApplication.class, args);
        SpringApplication.run(WebcaffeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AddressPojo addr = new AddressPojo(0, "Самара", "Ново-Садовая", 112);
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
        orderService.save(1, 1, order);
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