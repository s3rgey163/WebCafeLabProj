package ru.ssau.webcaffe.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.ssau.webcaffe.entity.Customer;
import ru.ssau.webcaffe.entity.User;
import ru.ssau.webcaffe.exception.UserPersistanceException;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.repo.CustomerRepository;

import java.security.Principal;

@Service
@Primary
public class DefaultCustomerService {
    private CustomerRepository customerRepository;

    private UserService userService;

    public DefaultCustomerService(CustomerRepository customerRepository, UserService userService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    public CustomerPojo getByUserId(long userId) {
        Customer customer = customerRepository.getCustomerByUserId(userId).orElseThrow(() ->
                new UserPersistanceException("Customer with user id[%d] not found".formatted(userId))
        );
        return CustomerPojo.ofEntity(customer);
    }

    public CustomerPojo getByUser(User user) {
        Customer customer = customerRepository.getCustomerByUser(user).orElseThrow(() ->
                new UserPersistanceException("Customer with user [%s] not found".formatted(user.getEmail()))
        );
        return CustomerPojo.ofEntity(customer);
    }

    public CustomerPojo getByFullName(String firstName, String secondName, String middleName) {
        Customer customer = customerRepository.getCustomerByFullName(firstName, secondName, middleName).orElseThrow(() ->
                new UserPersistanceException("Customer [%s %s %s] not found".formatted(secondName, firstName, middleName))
        );
        return CustomerPojo.ofEntity(customer);
    }

    public CustomerPojo getByPrincipal(Principal principal) {
        return userService.getUserByPrincipal(principal).getCustomer();
    }
}
