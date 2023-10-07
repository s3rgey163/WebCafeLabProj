package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table
@Data public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String code;

    private int validDays;

    private int minPrice;

    private int discount;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<ProductType> productTypes;

    public Promotion() {

    }
    public Promotion(
            long id,
            String code,
            int validDays,
            int discount,
            Set<ProductType> productTypes
    ) {
        this.id = id;
        this.code = code;
        this.validDays = validDays;
        this.discount = discount;
        this.productTypes = productTypes;
    }



    private static String generateCode() {
        return null;
    }

    public static void main(String[] args) {
        Promotion p = new Promotion();
        System.out.println(p.hashCode());
    }
}
