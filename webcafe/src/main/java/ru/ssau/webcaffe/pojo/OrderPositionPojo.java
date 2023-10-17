package ru.ssau.webcaffe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;
import ru.ssau.webcaffe.entity.Order;
import ru.ssau.webcaffe.entity.OrderPosition;
import ru.ssau.webcaffe.entity.ProductType;

@AllArgsConstructor
@NoArgsConstructor
@Data public class OrderPositionPojo {
    private ProductTypePojo type;

    private int count;

    public static OrderPositionPojo ofEntity(OrderPosition position) {
        return new OrderPositionPojo(
                ProductTypePojo.ofEntity(position.getId().getType()),
                position.getCount()
        );
    }

    public OrderPosition toEntity() {
        return new OrderPosition(type.toEntity(), null, count);
    }
}
