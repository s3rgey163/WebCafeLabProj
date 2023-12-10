package ru.ssau.webcaffe.service;

import ru.ssau.webcaffe.pojo.AddressPojo;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.pojo.UserPojo;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;

public interface AddressService {
    AddressPojo getById(long addressId);
    Set<AddressPojo> getByPrincipal(Principal principal);

    Set<AddressPojo> getByUserId(long userId);

    void save(long userId, AddressPojo address);

    void save(Principal principal, AddressPojo address);
    void save(UserPojo userPojo, AddressPojo address);

    void save(AddressPojo addressPojo);

    void save(Collection<AddressPojo> addressPojos);

    void delete(long customerId, long addressId);

    void deleteAllByCustomerId(long customerId);

    void deleteAllByCustomer(CustomerPojo customerId);

    void update(long addressId, AddressPojo newAddress);

    void update(AddressPojo addressPojo, AddressPojo newAddress);
}
