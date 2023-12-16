package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime birthday;

    @OneToOne
    @JoinColumn(name = User.PK_NAME, nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @ToString.Exclude
    private Set<Address> addresses;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private Set<Order> orders;

    public Customer(
            long id,
            String name,
            String secondName,
            String middleName,
            LocalDateTime birthday,
            User user,
            Set<Address> addresses,
            Set<Order> orders
    ) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.user = user;
        this.addresses = addresses;
        setOrders(orders);
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
        if(orders != null) {
            orders.forEach(order -> order.setCustomer(this));
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
