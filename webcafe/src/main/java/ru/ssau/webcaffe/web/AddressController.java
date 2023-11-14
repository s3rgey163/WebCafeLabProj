package ru.ssau.webcaffe.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ssau.webcaffe.entity.Address;
import ru.ssau.webcaffe.pojo.AddressPojo;
import ru.ssau.webcaffe.service.DefaultAddressService;
import ru.ssau.webcaffe.service.DefaultUserDetailService;

import java.util.Set;

@RestController
@RequestMapping("/api")
@PreAuthorize("permitAll()")
public class AddressController {


    @Autowired
    private DefaultAddressService defaultAddressService;

    @GetMapping("/{userId}/addresses")
    public Set<AddressPojo> getAddressesByUserId(@PathVariable long userId) {
        return defaultAddressService.getAddressesByUserId(userId);
    }
}
