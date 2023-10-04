package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Entity
@Table
@Data public class Product {

    private static final String JOIN_CATEGORY_ID = "category_id";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = JOIN_CATEGORY_ID)
    private Category category;

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<ProductType> types;

    public void setTypes(Set<ProductType> types) {
        this.types = types;
        this.types.forEach(s -> s.setProduct(this));
    }
}
