package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.Address;
import ru.ssau.webcaffe.pojo.AddressPojo;
import ru.ssau.webcaffe.repo.AddressRepository;
import ru.ssau.webcaffe.repo.UserRepository;
import ru.ssau.webcaffe.util.Util;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Service
@Primary
public class DefaultAddressService implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;

    public DefaultAddressService(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    @Override
    public Set<AddressPojo> getAddressesByPrincipal(Principal principal) {
        var user = userService.getUserByPrincipal(principal);
        Set<Address> addresses =  addressRepository.getAddressesByCustomerId(
                user.getCustomer().getId()
        );
        return Util.mapCollection(addresses, AddressPojo::ofEntity, HashSet::new);
    }

    public Set<AddressPojo> getAddressesByUserId(long userId) {
        Set<Address> addresses = addressRepository.getAddressesByUserId(userId);
        return Util.mapCollection(addresses, AddressPojo::ofEntity, HashSet::new);
    }
}
