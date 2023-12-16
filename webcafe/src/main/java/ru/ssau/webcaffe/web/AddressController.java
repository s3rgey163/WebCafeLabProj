package ru.ssau.webcaffe.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ssau.webcaffe.payload.responce.MessageResponse;
import ru.ssau.webcaffe.pojo.AddressPojo;
import ru.ssau.webcaffe.service.DefaultAddressService;

import java.security.Principal;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/address")
@PreAuthorize("permitAll()")
public class AddressController {
    private final DefaultAddressService addressService;

    public AddressController(DefaultAddressService addressService) {
        this.addressService = addressService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Set<AddressPojo> getAddressesByUserId(@PathVariable long userId) {
        return addressService.getByUserId(userId);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public Set<AddressPojo> getAddressesByPrincipal(Principal principal) {
        return addressService.getByPrincipal(principal);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("create")
    public MessageResponse createAddress(
            Principal principal,
            @RequestBody AddressPojo addressPojo
    ) {
        addressService.save(principal, addressPojo);
        return new MessageResponse("Address %s successfully created".formatted(addressPojo));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("user/{userId}/create")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse createAddress(
            @PathVariable long userId,
            @RequestBody AddressPojo addressPojo
    ) {
        addressService.save(userId, addressPojo);
        return new MessageResponse("Address %s successfully created".formatted(addressPojo));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{addressId}/update")
    public MessageResponse updateAddressById(@PathVariable long addressId, @RequestBody AddressPojo addressPojo) {
        addressService.update(addressId, addressPojo);
        return new MessageResponse("Address successfully updated to %s".formatted(addressPojo));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{addressId}/delete")
    public MessageResponse deleteAddressByPrincipal(Principal principal, @PathVariable long addressId) {
        addressService.deleteByUserAndAddressId(principal, addressId);
        return new MessageResponse("Address successfully deleted");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{addressId}/user/{userId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse deleteAddressByUserId(@PathVariable long userId, @PathVariable long addressId) {
        addressService.deleteByUserIdAndAddressId(userId, addressId);
        return new MessageResponse("Address successfully deleted");
    }
}
