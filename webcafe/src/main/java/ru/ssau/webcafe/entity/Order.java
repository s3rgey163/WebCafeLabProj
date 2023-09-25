package ru.ssau.webcafe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table
@Data public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToMany(mappedBy = "id")
    private List<Product> products;

    @ManyToOne(optional = false)
    private Promotion promotion;

    private Date date;

    private String commentary;
}
