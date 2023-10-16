package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data public class Product {
    public static final String FK_NAME = "product_id";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = Category.FK_NAME)
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
