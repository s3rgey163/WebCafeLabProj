package ru.ssau.webcafe.entity;

import jakarta.persistence.*;

@Entity
@Table
public class StructurePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Product product;
    @Embedded
    private Volume volume;

    private int price;

    private String describe;
}
