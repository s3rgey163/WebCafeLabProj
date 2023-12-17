package ru.ssau.webcaffe.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ssau.webcaffe.payload.request.SignupRequest;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserPojo> getUserById(@PathVariable long userId) {
        return new ResponseEntity<>(
                userService.getUserById(userId, true),
                HttpStatus.OK
        );
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(
            @RequestBody SignupRequest signupRequest,
            Principal principal
    ) {
        return new ResponseEntity<>(
                userService.update(signupRequest, principal),
                HttpStatus.OK
        );
    }
}
