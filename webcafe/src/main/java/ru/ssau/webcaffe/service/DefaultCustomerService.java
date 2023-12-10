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
import ru.ssau.webcaffe.util.Util;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Service
@Primary
public class DefaultCustomerService {
    private final CustomerRepository customerRepository;

    private final UserService userService;

    private final AddressService addressService;

    private final AddressRepository addressRepository;

    private final DefaultOrderService orderService;


    public DefaultCustomerService(CustomerRepository customerRepository, UserService userService, AddressService addressService, AddressRepository addressRepository, DefaultOrderService orderService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
        this.orderService = orderService;
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
    public void save(long userId, CustomerPojo customerPojo) {
        UserPojo userPojo = userService.getUserById(userId, true);
        CustomerPojo currentCustomerPojo = userPojo.getCustomer();
        if(currentCustomerPojo != null && !currentCustomerPojo.equals(customerPojo)) {
            addressService.deleteAllFromCustomer(currentCustomerPojo);
            customerRepository.deleteById(currentCustomerPojo.getId());
        }
        Customer customer = customerPojo.toEntity();
        customer.setUser(userPojo.toEntity());
        if(customer.getOrders() != null) {
            customer.getOrders().forEach(order -> order.setCustomer(customer));
        }
        if(customer.getAddresses() != null) {
            addressRepository.saveAll(customer.getAddresses());
        }
        customerRepository.save(customer);
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

    public void updateOrders(long customerId, Collection<OrderPojo> orders) {
        customerRepository.updateOrders(
                customerId,
                Util.mapCollection(orders, OrderPojo::toEntity, HashSet::new)
        );
    }

    public void addOrders(long customerId, OrderPojo... orders) {
        var currentOrders = orderService.getByCustomerIdOrderByDateTimeDesc(customerId);
        currentOrders.addAll(Arrays.stream(orders).toList());
        updateOrders(customerId, currentOrders);
    }

    public void deleteByCustomerId(long customerId) {
        customerRepository.deleteById(customerId);
    }
}
