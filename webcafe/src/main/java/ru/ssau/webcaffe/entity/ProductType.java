package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_type")
@AllArgsConstructor
@NoArgsConstructor
@Data public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Embedded
    private Weight weight;

    @ManyToOne
    @JoinColumn(name = Product.FK_NAME)
    private Product product;

    private BigDecimal price;

    private String describe;
}
