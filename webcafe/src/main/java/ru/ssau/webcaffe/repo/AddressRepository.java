package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}