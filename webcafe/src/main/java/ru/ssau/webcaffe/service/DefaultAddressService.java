package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.Address;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.User;
import ru.ssau.webcaffe.exception.EntityPersistenceException;
import ru.ssau.webcaffe.pojo.AddressPojo;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.pojo.UserPojo;
import ru.ssau.webcaffe.repo.AddressRepository;
import ru.ssau.webcaffe.repo.CustomerRepository;
import ru.ssau.webcaffe.util.Util;
import java.security.Principal;
import java.util.*;

@Service
@Primary
public class DefaultAddressService implements AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository  customerRepository;
    private final UserService userService;

    public DefaultAddressService(AddressRepository addressRepository, CustomerRepository customerRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    @Override
    public AddressPojo getById(long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() ->
                new EntityPersistenceException("Address with id [%d] not found".formatted(addressId))
        );
        return AddressPojo.ofEntity(address);
    }

    @Override
    public Set<AddressPojo> getByPrincipal(Principal principal) {
        var user = userService.getUserByPrincipal(principal);
        Set<Address> addresses =  addressRepository.getAddressesByCustomerId(
                user.getCustomer().getId()
        );
        return Util.mapCollection(addresses, AddressPojo::ofEntity, HashSet::new);
    }

    @Override
    public Set<AddressPojo> getByUserId(long userId) {
        Set<Address> addresses = addressRepository.getAddressesByUserId(userId);
        return Util.mapCollection(addresses, AddressPojo::ofEntity, HashSet::new);
    }

    @Transactional
    @Override
    public void save(long userId, AddressPojo address) {
        save(userService.getUserById(userId), address);
    }

    @Transactional
    @Override
    public void save(Principal principal, AddressPojo address) {
        save(userService.getUserByPrincipal(principal), address);
    }

    @Transactional
    @Override
    public void save(UserPojo userPojo, AddressPojo addressPojo) {
        User user = userPojo.toEntity();
        Customer customer = user.getCustomer();
        var address = addressPojo.toEntity();
        addressRepository.save(address);
        customer.getAddresses().add(address);
        customer.setUser(user);
        customerRepository.save(customer);
//        userService.saveUser(userPojo);
    }

    @Override
    public void save(AddressPojo addressPojo) {
        addressRepository.save(addressPojo.toEntity());
    }

    @Override
    public void save(Collection<AddressPojo> addressPojos) {
        var addresses = Util.mapCollection(addressPojos, AddressPojo::toEntity, ArrayList::new);
        addressRepository.saveAll(addresses);
    }

    @Override
    public void delete(long customerId, long addressId) {
        addressRepository.deleteAddressFromCustomer(customerId, addressId);
        if(addressRepository.getCountByAddressId(addressId) == 0) {
            addressRepository.deleteById(addressId);
        }
    }

    @Override
    public void deleteAllByCustomerId(long customerId) {
        var addresses = addressRepository.getAddressesByCustomerId(customerId);
        addressRepository.deleteAllFromCustomerId(customerId);
        addresses.forEach(address -> {
            if(addressRepository.getCountByAddressId(address.getId()) == 0) {
                addressRepository.deleteById(address.getId());
            }
        });
    }

    @Override
    public void deleteAllByCustomer(CustomerPojo customer) {
        deleteAllByCustomerId(customer.getId());
    }

    @Override
    public void update(long addressId, AddressPojo newAddress) {
        addressRepository.update(
                addressId,
                newAddress.getState(),
                newAddress.getStreet(),
                newAddress.getApartment()
        );
    }

    @Override
    public void update(AddressPojo addressPojo, AddressPojo newAddress) {
        addressPojo.setApartment(newAddress.getApartment());
        addressPojo.setState(addressPojo.getState());
        addressPojo.setStreet(newAddress.getStreet());
        addressRepository.save(addressPojo.toEntity());
    }
}
