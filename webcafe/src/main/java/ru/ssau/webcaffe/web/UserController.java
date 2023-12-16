package ru.ssau.webcaffe.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ssau.webcaffe.pojo.UserPojo;
import ru.ssau.webcaffe.service.UserService;
import ru.ssau.webcaffe.service.ValidationErrorResponse;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserPojo> getUserByPrincipal(Principal principal) {
         return new ResponseEntity<>(
                 userService.getUserByPrincipal(principal, true),
                 HttpStatus.OK
         );
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserPojo> getUserById(@PathVariable long userId) {
        return new ResponseEntity<>(
                userService.getUserById(userId, true),
                HttpStatus.OK
        );
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(
            @Valid @RequestBody UserPojo userPojo,
            Principal principal
    ) {
        return new ResponseEntity<>(
                userService.update(userPojo, principal),
                HttpStatus.OK
        );
    }
}
