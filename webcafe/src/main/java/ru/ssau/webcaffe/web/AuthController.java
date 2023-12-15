package ru.ssau.webcaffe.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import ru.ssau.webcaffe.payload.request.LoginRequest;
import ru.ssau.webcaffe.payload.request.SignupRequest;
import ru.ssau.webcaffe.payload.responce.JWTResponse;
import ru.ssau.webcaffe.payload.responce.MessageResponse;
import ru.ssau.webcaffe.security.JWTTokenProvider;
import ru.ssau.webcaffe.service.AuthService;
import ru.ssau.webcaffe.service.UserService;
import ru.ssau.webcaffe.service.ValidationErrorResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.register(signupRequest));
    }

}
