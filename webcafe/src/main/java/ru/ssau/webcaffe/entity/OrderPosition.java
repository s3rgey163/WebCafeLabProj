package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table
@Data public class OrderPosition {
    @EmbeddedId
    private Id id;

    private int count;

    public OrderPosition() {
    }

    public OrderPosition(Id id, int count) {
        this.id = id;
        this.count = count;
    }

    public OrderPosition(ProductType type, Order order, int count) {
        this.id = new Id(type, order);
        this.count = count;
    }

    @Embeddable
    @Data public static class Id implements Serializable {
        @ManyToOne
        @JoinColumn(name = "product_type_id")
        private ProductType type;
        @ManyToOne
        @JoinColumn(name = "customer_order_id")
        private Order order;

        public Id() {

        }

        public Id(ProductType type, Order order) {
            this.type = type;
            this.order = order;
        }
    }
}
