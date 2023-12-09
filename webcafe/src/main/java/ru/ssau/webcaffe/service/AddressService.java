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

    AddressPojo deleteAddress(CustomerPojo customerPojo, long addressId);

    AddressPojo deleteAddress(CustomerPojo customerPojo, AddressPojo addressPojo);

    AddressPojo deleteAddress(long customerId, long addressId);

    AddressPojo deleteAddress(long customerId, AddressPojo addressPojo);

    void deleteAllAddresses(long customerId);

    void deleteAllAddresses(CustomerPojo customerId);
}
