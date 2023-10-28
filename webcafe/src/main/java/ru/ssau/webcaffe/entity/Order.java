package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer_order")
@AllArgsConstructor
@NoArgsConstructor
@Data public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(
            mappedBy = "id.order",
            cascade = CascadeType.ALL
    )
    @Column(nullable = false)
    private Set<OrderPosition> positions;

    @ManyToOne
    private Promotion promotion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDateTime date;

    private String commentary;
}
