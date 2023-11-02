package ru.ssau.webcaffe.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ssau.webcaffe.service.DefaultUserDetailService;

import java.io.IOException;
import java.util.Collections;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private JWTTokenProvider provider;
    private DefaultUserDetailService userDetailsService;

    @Autowired
    public JWTFilter(JWTTokenProvider provider, DefaultUserDetailService userDetailsService) {
        this.provider = provider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if(token != null && provider.validateToken(token)) {
            long userId = provider.getUserIdByToken(token);
            var user = userDetailsService.getUserById(userId);
            var auth = new UsernamePasswordAuthenticationToken(
                    user, null, Collections.emptyList()
            );
            auth.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = (String) SecurityAttributes.HEADER_STRING.getValue();
        String tokenPrefix = (String) SecurityAttributes.TOKEN_PREFIX.getValue();
        String bearer = request.getHeader(header);
        if (StringUtils.hasText(bearer) && bearer.startsWith(tokenPrefix)) {
            return bearer.split(" ")[1];
        }
        return null;
    }
}