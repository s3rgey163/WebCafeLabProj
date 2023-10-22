package ru.ssau.webcaffe.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.User;
import ru.ssau.webcaffe.util.Util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data public class CustomerPojo {
    private long id;
    private String name;

    private String secondName;

    private String middleName;

    private Date birthday;

    private UserPojo userPojo;

    private Set<AddressPojo> addressPojos;

    private Set<OrderPojo> orderPojos;

    public static CustomerPojo ofEntity(Customer customer) {
        return CustomerPojo.builder()
                .id(customer.getId())
                .name(customer.getName())
                .secondName(customer.getSecondName())
                .middleName(customer.getMiddleName())
                .birthday((Date) customer.getBirthday().clone())
                .addressPojos(customer.getAddresses() == null
                        ? null
                        : Util.collectionMapper(customer.getAddresses(), AddressPojo::ofEntity, HashSet::new)
                )
                .orderPojos(customer.getOrders() == null
                        ? null
                        : Util.collectionMapper(customer.getOrders(), OrderPojo::ofEntity, HashSet::new)
                ).build();
    }

    public Customer toEntity() {
        return new Customer(
                id,
                name,
                secondName,
                middleName,
                (Date) birthday.clone(),
                userPojo.toEntity(),
                addressPojos == null
                        ? null
                        : Util.collectionMapper(addressPojos, AddressPojo::toEntity, HashSet::new),
                orderPojos == null
                        ? null
                        : Util.collectionMapper(orderPojos, OrderPojo::toEntity, HashSet::new)
        );
    }
}
