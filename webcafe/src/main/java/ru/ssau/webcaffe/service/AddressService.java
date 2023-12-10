package ru.ssau.webcaffe.service;

import ru.ssau.webcaffe.pojo.AddressPojo;
import ru.ssau.webcaffe.pojo.CustomerPojo;

import java.security.Principal;
import java.util.Set;

public interface AddressService {
    AddressPojo getAddressById(long addressId);
    Set<AddressPojo> getAddressesByPrincipal(Principal principal);

    Set<AddressPojo> getAddressesByUserId(long userId);

    void saveAddress(long customerId, AddressPojo address);

    void saveAddress(CustomerPojo customerPojo, AddressPojo address);

    void deleteAddress(long customerId, long addressId);

    void deleteAllAddresses(long customerId);

    void deleteAllAddresses(CustomerPojo customerId);

    void updateAddress(long addressId, AddressPojo newAddress);

    void updateAddress(AddressPojo addressPojo, AddressPojo newAddress);
}
