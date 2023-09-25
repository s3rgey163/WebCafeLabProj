package ru.ssau.webcafe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.util.List;

@Entity
@Table
@Data public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToMany
    private List<Product> products;

    private String code;
}
