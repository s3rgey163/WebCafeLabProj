package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    private Date birthday;

    @OneToOne
    @JoinColumn(name = User.PK_NAME, nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Address> addresses;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "customer",
            cascade = CascadeType.ALL
    )
    private Set<Order> orders;
}
