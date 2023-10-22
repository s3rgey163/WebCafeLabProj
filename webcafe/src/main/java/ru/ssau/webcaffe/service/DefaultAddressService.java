package ru.ssau.webcaffe.service;

import ru.ssau.webcaffe.repo.AddressRepository;

public class DefaultAddressService implements AddressService {
    private AddressRepository addressRepository;

    public DefaultAddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
}
