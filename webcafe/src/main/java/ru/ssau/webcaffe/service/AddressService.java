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

    void save(long userId, Collection<AddressPojo> address);

    void save(Principal principal, Collection<AddressPojo> address);
    void save(UserPojo userPojo, Collection<AddressPojo> address);

    void save(AddressPojo addressPojo);

    void save(Collection<AddressPojo> addressPojos);

    void deleteByUserIdAndAddressId(long userId, long addressId);

    void deleteByUserAndAddressId(Principal principal, long addressId);

    void deleteByUserId(long userId, Collection<AddressPojo> addressPojos);

    void deleteAllByUserId(long userId);

    void deleteAllByUser(Principal principal);

    void update(long addressId, AddressPojo newAddress);

    void update(AddressPojo addressPojo, AddressPojo newAddress);
}
