package ru.ssau.webcaffe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ssau.webcaffe.entity.Address;
import ru.ssau.webcaffe.entity.Category;

/**
 * DTO for {@link Address}
 */
@AllArgsConstructor
@Data public class AddressPojo {
    private long id;
    private String state;

    private String street;

    private int apartment;

    public static AddressPojo ofEntity(Address address) {
        return new AddressPojo(
                address.getId(),
                address.getState(),
                address.getStreet(),
                address.getApartment()
        );
    }

    public Address toEntity() {
        return new Address(id, state, street, apartment);
    }

    @Override
    public String toString() {
        return "%s %s кв. %d".formatted(
                state, state, apartment
        );
    }
}
