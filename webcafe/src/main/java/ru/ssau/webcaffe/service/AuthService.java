package ru.ssau.webcaffe.service;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.ssau.webcaffe.payload.request.LoginRequest;
import ru.ssau.webcaffe.payload.request.SignupRequest;
import ru.ssau.webcaffe.payload.responce.JWTResponse;
import ru.ssau.webcaffe.payload.responce.MessageResponse;
import ru.ssau.webcaffe.security.JWTTokenProvider;

@Service
@Validated
public class AuthService {
    public static final Logger lg = LoggerFactory.getLogger(AuthService.class);
    private final UserService userService;

    private final JWTTokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserService userService,
            JWTTokenProvider tokenProvider,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public JWTResponse authenticate(@Valid LoginRequest loginRequest) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                );
        Authentication authenticationResponse = this.authenticationManager
                .authenticate(authenticationRequest);
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        lg.debug("User login: {}", authenticationResponse.getPrincipal());
        return new JWTResponse(tokenProvider.generateToken(authenticationResponse), true);
    }

    public MessageResponse register(@Valid SignupRequest signup) {
        var user = userService.createUser(signup);
        lg.debug("User registry: {}", user);
        return new MessageResponse("User register successfully");
    }

    public Authentication getAuthInfo() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
