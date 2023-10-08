package ru.ssau.webcaffe.pojo;

import jakarta.persistence.*;
import lombok.Data;
import ru.ssau.webcaffe.entity.Address;
import ru.ssau.webcaffe.entity.Order;

import java.util.Date;
import java.util.Set;

@Data public class CutomerPojo {
    private long id;
    private String name;

    private String secondName;

    private String middleName;

    private Date birthday;

    private Set<Address> addresses;

    private Set<Order> orders;
}
