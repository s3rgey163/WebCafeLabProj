package ru.ssau.webcaffe.pojo;

import lombok.*;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.util.Util;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link Customer}
 */
@Builder
@Value
public class CustomerPojo {
    private long id;
    private String name;

    private String secondName;

    private String middleName;

    private LocalDateTime birthday;

    private Set<AddressPojo> addressPojos;

    private Set<OrderPojo> orderPojos;

    public static CustomerPojo ofEntity(Customer customer) {
        if(customer == null) return null;
        return CustomerPojo.builder()
                .id(customer.getId())
                .name(customer.getName())
                .secondName(customer.getSecondName())
                .middleName(customer.getMiddleName())
                .birthday(customer.getBirthday())
                .addressPojos(customer.getAddresses() == null
                        ? null
                        : Util.mapPersistenceCollection(customer.getAddresses(), AddressPojo::ofEntity, HashSet::new)
                )
                .orderPojos(customer.getOrders() == null
                        ? null
                        : Util.mapPersistenceCollection(customer.getOrders(), OrderPojo::ofEntity, HashSet::new)
                ).build();
    }

    public Customer toEntity() {
        return new Customer(
                id,
                name,
                secondName,
                middleName,
                birthday,
                null,
                addressPojos == null
                        ? null
                        : Util.mapPersistenceCollection(addressPojos, AddressPojo::toEntity, HashSet::new),
                orderPojos == null
                        ? null
                        : Util.mapPersistenceCollection(orderPojos, OrderPojo::toEntity, HashSet::new)
        );
    }
}
