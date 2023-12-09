package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.User;
import ru.ssau.webcaffe.exception.EntityPersistenceException;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.repo.CustomerRepository;

import java.security.Principal;

@Service
@Primary
public class DefaultCustomerService {
    private final CustomerRepository customerRepository;

    private final UserService userService;

    private final AddressService addressService;

    private final DefaultOrderService orderService;

    public DefaultCustomerService(
            CustomerRepository customerRepository,
            UserService userService,
            AddressService addressService,
            DefaultOrderService orderService
    ) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.addressService = addressService;
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
        return userService.getUserByPrincipal(principal).getCustomer();
    }

    public void saveCustomer(CustomerPojo customerPojo) {

    }

    @Transactional
    public CustomerPojo deleteCustomer(long customerId) {
        return null;
    }
}
