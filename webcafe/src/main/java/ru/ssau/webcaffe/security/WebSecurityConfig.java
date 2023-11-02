package ru.ssau.webcaffe.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.ssau.webcaffe.repo.UserRepository;
import ru.ssau.webcaffe.service.DefaultUserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    private JWTAuthenticationEntryPoint jwtEntryPoint;
    @Getter
    private JWTFilter jwtFilter;

    @Autowired
    public WebSecurityConfig(
            JWTAuthenticationEntryPoint jwtEntryPoint,
            JWTFilter jwtFilter
    ) {
        this.jwtEntryPoint = jwtEntryPoint;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain newSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.cors(Customizer.withDefaults())
                .exceptionHandling(exh -> exh.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(smc -> smc.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)
                ).authorizeHttpRequests(amr -> amr
                        .requestMatchers(
                                (String) SecurityAttributes.SIGN_UP_URLS.getValue()
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder newPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService newUserDetailsService(UserRepository repository) {
        return new DefaultUserDetailService(repository);
    }

}
