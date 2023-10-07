package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ProductCategory")
@Data public class Category {
    public static final String FK_NAME = "category_id";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    private String describe;


    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Product> products;

    public Category() {}

    public Category(String name) {
        this(0, name);
    }

    public Category(String name, String describe) {
        this(0, name, describe, null);
    }

    public Category(long id, String name) {
        this(0, name, null, null);
    }

    public Category(String name, String describe, List<Product> products) {
        this(0, name, describe, products);
    }

    public Category(
            long id,
            String name,
            String describe,
            List<Product> products
    ) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.products = products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        this.products.forEach(p ->  p.setCategory(this));
    }
}
