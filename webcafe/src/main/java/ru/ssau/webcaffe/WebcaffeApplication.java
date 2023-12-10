package ru.ssau.webcaffe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.webcaffe.pojo.*;
import ru.ssau.webcaffe.repo.AddressRepository;
import ru.ssau.webcaffe.service.*;
import ru.ssau.webcaffe.util.Util;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@SpringBootApplication
public class WebcaffeApplication implements CommandLineRunner {

    private final AddressService addressService;
    private final DefaultCustomerService customerService;

    private final DefaultOrderService orderService;

    private final DefaultProductService productService;

    private final DefaultProductTypeService productTypeService;

    private final DefaultPromotionService promotionService;

    private final UserService userService;

    @Autowired
    private AddressRepository addressRepository;

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
        customerService.updateOrders(752, List.of(
                        new OrderPojo(0, LocalDateTime.now(), new PromotionPojo(), "Перемога", Collections.emptySet()),
                        new OrderPojo(0, LocalDateTime.now(), new PromotionPojo(), "Зрада", Collections.emptySet())
                )
        );

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