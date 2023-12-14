package ru.ssau.webcaffe.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ssau.webcaffe.pojo.AddressPojo;
import ru.ssau.webcaffe.service.DefaultAddressService;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("permitAll()")
public class AddressController {


    @Autowired
    private DefaultAddressService defaultAddressService;

    @GetMapping("{userId}/addresses")
    public Set<AddressPojo> getAddressesByUserId(@PathVariable long userId) {
        return defaultAddressService.getByUserId(userId);
    }

    @PostMapping("{userId}/addresses")
    public void createAddress(
            @PathVariable long userId,
            @RequestBody AddressPojo addressPojo
    ) {
        defaultAddressService.save(userId, addressPojo);
    }
}
