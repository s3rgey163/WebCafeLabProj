package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
import java.util.List;

@Entity
@Table
@Data public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @Column(nullable = false)
    private List<Product> products;

    @ManyToOne
    private Promotion promotion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    private String commentary;
}
