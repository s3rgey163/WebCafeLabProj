package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.webcaffe.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}