package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.entity.Order;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.pojo.OrderPojo;
import ru.ssau.webcaffe.pojo.UserPojo;
import ru.ssau.webcaffe.repo.OrderRepository;
import ru.ssau.webcaffe.util.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class DefaultOrderService {
    private OrderRepository orderRepository;

    private DefaultCustomerService customerService;

    public DefaultOrderService(OrderRepository orderRepository, DefaultCustomerService customerService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
    }

    private List<OrderPojo> mapToOrderPojoCollection(List<Order> orders) {
        return Util.mapCollection(orders, OrderPojo::ofEntity, ArrayList::new);
    }

    public List<OrderPojo> getByCustomerIdAndDateTimeBetweenOrderByDateTime(
            long customerId,
            LocalDateTime when,
            LocalDateTime until
    ) {
        var orders = orderRepository.getByCustomerIdAndDateTimeBetweenOrderByDateTime(
                customerId,
                when,
                until
        );
        return mapToOrderPojoCollection(orders);
    }

    public List<OrderPojo> getByCustomerIdAndDateTimeBetweenOrderByDateTimeDesc(
            long customerId,
            LocalDateTime when,
            LocalDateTime until
    ) {
        var orders = orderRepository.getByCustomerIdAndDateTimeBetweenOrderByDateTimeDesc(
                customerId,
                when,
                until
        );
        return mapToOrderPojoCollection(orders);
    }
    public List<OrderPojo> getByCustomerIdOrderByDateTime(long customerId) {
        var orders = orderRepository.getByCustomerIdOrderByDateTime(customerId);
        return mapToOrderPojoCollection(orders);
    }

    public List<OrderPojo> getByDateOrderByDate(int year, int month, int day) {
        var orders = orderRepository.getByDateOrderByDate(year, month, day);
        return mapToOrderPojoCollection(orders);
    }

    public List<OrderPojo> getByDateOrderByDateDesc(int year, int month, int day) {
        var orders = orderRepository.getByDateOrderByDateDesc(year, month, day);
        return mapToOrderPojoCollection(orders);
    }

    public List<OrderPojo> getByDateTimeBetweenOrderByDateTime(LocalDateTime when, LocalDateTime until) {
        var orders = orderRepository.getByDateTimeBetweenOrderByDateTime(when, until);
        return mapToOrderPojoCollection(orders);
    }

    public List<OrderPojo> getByDateTimeBetweenOrderByDateTimeDesc(LocalDateTime when, LocalDateTime until) {
        var orders = orderRepository.getByDateTimeBetweenOrderByDateTimeDesc(when, until);
        return mapToOrderPojoCollection(orders);
    }

    public List<OrderPojo> getByCustomerIdOrderByDateTimeDesc(long customerId) {
        var orders = orderRepository.getByCustomerIdOrderByDateTimeDesc(customerId);
        return mapToOrderPojoCollection(orders);
    }

    public void save(long userId, OrderPojo orderPojo) {
        customerService.addOrdersByUserId(userId, orderPojo);
    }

    public void deleteByOrderIdAndCustomerId(long userId, long orderId) {
        orderRepository.deleteByIdAndCustomerId(orderId, userId);
    }

    public void deleteByIdAndUserId(long userId, long orderId) {
        orderRepository.deleteByIdAndCustomerId(orderId, userId);
    }
}
