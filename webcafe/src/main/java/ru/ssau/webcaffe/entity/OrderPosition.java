package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table
@Data public class OrderPosition {
/*    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType type;
    @ManyToOne
    @JoinColumn(name = "customer_order_id")
    private Order order;*/
    @EmbeddedId
    private OrderPositionId id;

    private int count;

    @Embeddable
    @Data static class OrderPositionId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "product_type_id")
        private ProductType type;
        @ManyToOne
        @JoinColumn(name = "customer_order_id")
        private Order order;
    }
}
