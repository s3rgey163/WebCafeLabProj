package ru.ssau.webcaffe.service;

import ru.ssau.webcaffe.pojo.AddressPojo;

import java.security.Principal;
import java.util.Set;

public interface AddressService {
    Set<AddressPojo> getAddressesByPrincipal(Principal principal);

    Set<AddressPojo> getAddressesByUserId(long userId);
}
