package ru.ssau.webcaffe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.webcaffe.entity.Order;
import ru.ssau.webcaffe.entity.OrderPosition;
import ru.ssau.webcaffe.util.Util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPojo {
    private long id;

    private Date date;

    private PromotionPojo promotionPojo;

    private String commentary;

    private Set<OrderPositionPojo> positionPojos;

    public static OrderPojo ofEntity(Order order) {
        return new OrderPojo(
                order.getId(),
                (Date) order.getDate().clone(),
                order.getPromotion() == null
                        ? null
                        : PromotionPojo.ofEntity(order.getPromotion()),
                order.getCommentary(),
                order.getPositions() == null
                        ? null
                        : Util.collectionMapper(
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
                        : Util.collectionMapper(
                            positionPojos,
                            OrderPositionPojo::toEntity,
                            HashSet::new
                ),
                promotionPojo == null
                        ? null
                        : promotionPojo.toEntity(),
                (Date) date.clone(),
                commentary
        );
    }
}
