package ru.ssau.webcaffe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.Address;

import java.util.Set;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select c.addresses from Customer c where c.id = :id")
    Set<Address> getAddressesByCustomerId(long id);

    @Query("select c.addresses from Customer c join c.addresses join c.user u where u.id = :id")
    Set<Address> getAddressesByUserId(long id);

    @Query("select count(a) from Customer c join c.addresses a where a.id = :id")
    int getAddressesCountById(long id);

    @Modifying
    @Query(nativeQuery = true, value = "delete from customer_addresses where addresses_id = ?2 and customer_id = ?1")
    void deleteAddressFromCustomer(long customerId, long addressId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from customer_addresses where customer_id = ?1")
    void deleteAllFromCustomerId(long customerId);

    @Query("select count(*) from Customer c join c.addresses a where a.id = :id")
    long getCountByAddressId(@NonNull long id);

    @Transactional
    @Modifying
    @Query("update Address a set a.state = :state, a.street = :street, a.apartment = :apartment where a.id = :addressId")
    void update(long addressId, String state, String street, int apartment);
}