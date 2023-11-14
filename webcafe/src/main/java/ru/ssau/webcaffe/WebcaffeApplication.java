package ru.ssau.webcaffe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.webcaffe.service.AddressService;


@SpringBootApplication
public class WebcaffeApplication {

    private final AddressService addressService;

    public WebcaffeApplication(AddressService addressService) {
        this.addressService = addressService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebcaffeApplication.class, args);

    }
}