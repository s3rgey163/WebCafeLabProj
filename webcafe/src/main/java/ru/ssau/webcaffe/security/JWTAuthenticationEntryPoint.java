package ru.ssau.webcaffe.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.ssau.webcaffe.payload.responce.Response;

import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        String contentType = (String) SecurityAttributes.CONTENT_TYPE.getValue();
        response.setContentType(contentType);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(Response.INVALID_LOGIN.toJson());
        response.getWriter().println(authException.getMessage());
    }
}
