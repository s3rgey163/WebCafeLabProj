package ru.ssau.webcafe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Entity
@Table
@Data public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @OneToMany
    private Set<StructurePrice> structures;
}
