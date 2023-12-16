package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.Order;
import ru.ssau.webcaffe.entity.User;
import ru.ssau.webcaffe.exception.EntityPersistenceException;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.pojo.OrderPojo;
import ru.ssau.webcaffe.pojo.UserPojo;
import ru.ssau.webcaffe.repo.AddressRepository;
import ru.ssau.webcaffe.repo.CustomerRepository;
import ru.ssau.webcaffe.repo.UserRepository;
import ru.ssau.webcaffe.util.Util;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Primary
public class DefaultCustomerService {
    private final CustomerRepository customerRepository;

    private final UserService userService;

    private final AddressService addressService;


    private final DefaultOrderService orderService;
    private final UserRepository userRepository;


    public DefaultCustomerService(CustomerRepository customerRepository, UserService userService, AddressService addressService, DefaultOrderService orderService,
                                  UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.addressService = addressService;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    public CustomerPojo getByUserId(long userId) {
        Customer customer = customerRepository.getCustomerByUserId(userId).orElseThrow(() ->
                new EntityPersistenceException("Customer with user id[%d] not found".formatted(userId))
        );
        return CustomerPojo.ofEntity(customer);
    }


    public CustomerPojo getByCustomerId(long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new EntityPersistenceException("Custmer with id [%d] not found".formatted(customerId))
        );
        return CustomerPojo.ofEntity(customer);
    }

    public CustomerPojo getByUser(User user) {
        Customer customer = customerRepository.getCustomerByUser(user).orElseThrow(() ->
                new EntityPersistenceException("Customer with user [%s] not found".formatted(user.getEmail()))
        );
        return CustomerPojo.ofEntity(customer);
    }

    public CustomerPojo getByFullName(String firstName, String secondName, String middleName) {
        Customer customer = customerRepository.getCustomerByFullName(firstName, secondName, middleName).orElseThrow(() ->
                new EntityPersistenceException("Customer [%s %s %s] not found".formatted(secondName, firstName, middleName))
        );
        return CustomerPojo.ofEntity(customer);
    }

    public CustomerPojo getByPrincipal(Principal principal) {
        return userService.getUserByPrincipal(principal, false).getCustomer();
    }

    @Transactional
    public void save(Principal principal, CustomerPojo customerPojo) {
        long userId = userService.getUserIdByPrincipal(principal);
        save(userId, customerPojo);
    }

    @Transactional
    public void save(long userId, CustomerPojo customerPojo) {
        UserPojo userPojo = userService.getUserById(userId, false);
        CustomerPojo currentCustomerPojo = userPojo.getCustomer();
        Customer newCustomer = customerPojo.toEntity(userPojo.toEntity());
        if(currentCustomerPojo != null) {
            newCustomer.setId(currentCustomerPojo.getId());
            if(currentCustomerPojo.getAddressPojos() != null
                    && !currentCustomerPojo.getAddressPojos().isEmpty()) {
                addressService.deleteAllByUserId(userId);
            }
            if(currentCustomerPojo.getOrderPojos() != null
                    && !currentCustomerPojo.getOrderPojos().isEmpty()) {
                orderService.deleteAllByUserId(userId);
            }
        }
//            customerRepository.deleteById(currentCustomerPojo.getId());
//        if(newCustomer.getAddresses() != null) {
//            addressRepository.saveAll(newCustomer.getAddresses());
//        }
        customerRepository.save(newCustomer);
    }

    @Transactional
    public void updateByCustomerId(long customerId, CustomerPojo customerPojo) {
        save(userService.getUserIdByCustomerId(customerId), customerPojo);
    }

    public void updateFullNameAndBirthday(
            long customerId,
            String firstname,
            String secondname,
            String middlename,
            LocalDateTime birthday
    ) {
        customerRepository.updateFullNameAndBirthdayById(
                customerId,
                firstname,
                secondname,
                middlename,
                birthday
        );
    }
    public void addOrdersByUserId(long userId, OrderPojo... orders) {
        long customerId = customerRepository.getCustomerIdByUserId(userId);
        addOrdersByCustomerId(customerId, orders);
    }

    public void updateOrdersByUserId(long userId, Collection<OrderPojo> orderPojos) {
        long customerId = customerRepository.getCustomerIdByUserId(userId);
        updateOrdersByCustomerId(customerId, orderPojos);
    }

    public void updateOrdersByCustomerId(long customerId, Collection<OrderPojo> orderPojos) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new EntityPersistenceException("Customer with id[%d] not found"
                        .formatted(customerId)
        ));
        Set<Order> orders = Util.mapCollection(orderPojos, OrderPojo::toEntity, HashSet::new);
        orders.forEach(order -> order.setCustomer(customer));
        customer.setOrders(orders);
        customerRepository.save(customer);
    }

    public void addOrdersByCustomerId(long customerId, OrderPojo... orders) {
        var currentOrders = orderService.getByCustomerIdOrderByDateTimeDesc(customerId);
        currentOrders.addAll(Arrays.stream(orders).toList());
        updateOrdersByCustomerId(customerId, currentOrders);
    }

    public void deleteByCustomerId(long customerId) {
        customerRepository.deleteById(customerId);
    }

    public void deleteByUserId(long userId) {
        customerRepository.deleteByUserId(userId);
    }

    public void deleteByUser(Principal principal) {
        long userId = userService.getUserIdByPrincipal(principal);
        deleteByUserId(userId);
    }
}
