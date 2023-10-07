package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Embedded
    private Weight weight;

    @ManyToOne
    @JoinColumn(name = Product.FK_NAME)
    private Product product;

    private int price;

    private String describe;
}
