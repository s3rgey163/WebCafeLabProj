package ru.ssau.webcaffe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.webcaffe.entity.Category;
import ru.ssau.webcaffe.entity.Order;
import ru.ssau.webcaffe.util.Util;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link Order}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPojo {
    private long id;

    private LocalDateTime date;

    private PromotionPojo promotionPojo;

    private String commentary;

    private Set<OrderPositionPojo> positionPojos;

    public static OrderPojo ofEntity(Order order) {
        return new OrderPojo(
                order.getId(),
                order.getDateTime(),
                order.getPromotion() == null
                        ? null
                        : PromotionPojo.ofEntity(order.getPromotion()),
                order.getCommentary(),
                order.getPositions() == null
                        ? null
                        : Util.mapPersistenceCollection(
                        order.getPositions(),
                        OrderPositionPojo::ofEntity,
                        HashSet::new
                )
        );
    }

    public Order toEntity() {
        return new Order(
                id,
                null,
                positionPojos == null
                        ? null
                        : Util.mapPersistenceCollection(
                            positionPojos,
                            OrderPositionPojo::toEntity,
                            HashSet::new
                ),
                promotionPojo == null
                        ? null
                        : promotionPojo.toEntity(),
                date,
                commentary
        );
    }
}
