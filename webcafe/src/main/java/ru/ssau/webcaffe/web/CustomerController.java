package ru.ssau.webcaffe.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ssau.webcaffe.payload.responce.MessageResponse;
import ru.ssau.webcaffe.pojo.CustomerPojo;
import ru.ssau.webcaffe.service.DefaultCustomerService;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("api/customer")
@PreAuthorize("permitAll()")
public class CustomerController {
    private final DefaultCustomerService customerService;

    public CustomerController(DefaultCustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public CustomerPojo getCustomerById(@PathVariable long id) {
        return customerService.getByCustomerId(id);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public CustomerPojo getCustomerByPrincipal(Principal principal) {
        return customerService.getByPrincipal(principal);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("user/{id}")
    public CustomerPojo getCustomerByUserId(@PathVariable long id) {
        return customerService.getByUserId(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("create")
    public MessageResponse createCustomer(
            Principal principal,
            @RequestBody CustomerPojo customerPojo
    ) {
        customerService.save(principal, customerPojo);
        return new MessageResponse("Customer [%s] successfully created"
                .formatted(customerPojo)
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("user/{id}/create")
    public MessageResponse createCustomer(
            @PathVariable long id,
            @RequestBody CustomerPojo customerPojo
    ) {
        customerService.save(id, customerPojo);
        return new MessageResponse("Customer [%s] successfully created"
                .formatted(customerPojo)
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("user/delete")
    public MessageResponse deleteCustomerByUser(Principal principal) {
        customerService.deleteByUser(principal);
        return new MessageResponse("Customer with username[%s] successfully deleted"
                .formatted(principal.getName()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("user/{id}/delete")
    public MessageResponse deleteCustomerByUserId(@PathVariable long id) {
        customerService.deleteByUserId(id);
        return new MessageResponse("Customer with user id[%d] successfully deleted"
                .formatted(id)
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{id}/delete")
    public MessageResponse deleteCustomerByCustomerId(@PathVariable long id) {
        customerService.deleteByCustomerId(id);
        return new MessageResponse("Customer with id[%d] successfully deleted"
                .formatted(id)
        );
    }
}
