package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ssau.webcaffe.entity.Address;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select c.addresses from Customer c where c.id = :id")
    Set<Address> getAddressesByCustomerId(long id);

    @Query("select c.addresses from Customer c join c.addresses join c.user u where u.id = :id")
    Set<Address> getAddressesByUserId(long id);
}