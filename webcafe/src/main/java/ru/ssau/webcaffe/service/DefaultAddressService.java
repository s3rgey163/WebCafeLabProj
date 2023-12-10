package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.Address;
import ru.ssau.webcaffe.exception.EntityPersistenceException;
import ru.ssau.webcaffe.pojo.AddressPojo;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.repo.AddressRepository;
import ru.ssau.webcaffe.util.Util;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Service
@Primary
public class DefaultAddressService implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;

    private final DefaultCustomerService customerService;

    public DefaultAddressService(AddressRepository addressRepository, UserService userService, DefaultCustomerService customerService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.customerService = customerService;
    }

    @Override
    public AddressPojo getAddressById(long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() ->
                new EntityPersistenceException("Address with id [%d] not found".formatted(addressId))
        );
        return AddressPojo.ofEntity(address);
    }

    @Override
    public Set<AddressPojo> getAddressesByPrincipal(Principal principal) {
        var user = userService.getUserByPrincipal(principal);
        Set<Address> addresses =  addressRepository.getAddressesByCustomerId(
                user.getCustomer().getId()
        );
        return Util.mapCollection(addresses, AddressPojo::ofEntity, HashSet::new);
    }

    @Override
    public Set<AddressPojo> getAddressesByUserId(long userId) {
        Set<Address> addresses = addressRepository.getAddressesByUserId(userId);
        return Util.mapCollection(addresses, AddressPojo::ofEntity, HashSet::new);
    }

    @Transactional
    @Override
    public void saveAddress(long customerId, AddressPojo address) {
        saveAddress(customerService.getByCustomerId(customerId), address);
    }

    @Transactional
    @Override
    public void saveAddress(CustomerPojo customerPojo, AddressPojo address) {
        addressRepository.save(address.toEntity());
        customerPojo.getAddressPojos().add(address);
        customerService.saveCustomer(customerPojo);
    }

    @Override
    public void deleteAddress(long customerId, long addressId) {
        addressRepository.deleteAddressFromCustomer(customerId, addressId);
        if(addressRepository.getCountByAddressId(addressId) == 0) {
            addressRepository.deleteById(addressId);
        }
    }

    @Override
    public void deleteAllAddresses(long customerId) {
        var addresses = addressRepository.getAddressesByCustomerId(customerId);
        addressRepository.deleteAllFromCustomerId(customerId);
        addresses.forEach(address -> {
            if(addressRepository.getCountByAddressId(address.getId()) == 0) {
                addressRepository.deleteById(address.getId());
            }
        });
    }

    @Override
    public void deleteAllAddresses(CustomerPojo customer) {
        deleteAllAddresses(customer.getId());
    }

    @Override
    public void updateAddress(long addressId, AddressPojo newAddress) {
/*        Address address = addressRepository.findById(addressId).orElseThrow(() ->
                new EntityPersistenceException("Address with id[%d] not found".formatted(addressId))
        );
        updateAddress(AddressPojo.ofEntity(address), newAddress);*/
        addressRepository.update(
                addressId,
                newAddress.getState(),
                newAddress.getStreet(),
                newAddress.getApartment()
        );
    }

    @Override
    public void updateAddress(AddressPojo addressPojo, AddressPojo newAddress) {
        addressPojo.setApartment(newAddress.getApartment());
        addressPojo.setState(addressPojo.getState());
        addressPojo.setStreet(newAddress.getStreet());
        addressRepository.save(addressPojo.toEntity());
    }
}
