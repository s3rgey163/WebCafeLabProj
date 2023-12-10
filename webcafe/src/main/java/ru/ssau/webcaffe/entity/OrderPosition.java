package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class OrderPosition {
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        OrderPosition that = (OrderPosition) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}
