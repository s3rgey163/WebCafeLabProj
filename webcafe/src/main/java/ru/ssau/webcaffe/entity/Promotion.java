package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String code;

    private int validDays;

    private int minPrice;

    private int discount;

    @ManyToMany
    private Set<ProductType> productTypes;

    @ManyToMany
    private Set<ProductType> conditionProductTypes;
}
