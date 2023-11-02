package ru.ssau.webcaffe.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ssau.webcaffe.payload.request.LoginRequest;
import ru.ssau.webcaffe.payload.responce.JWTResponse;
import ru.ssau.webcaffe.security.JWTTokenProvider;
import ru.ssau.webcaffe.service.UserService;
import ru.ssau.webcaffe.service.ValidationErrorResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {
}
