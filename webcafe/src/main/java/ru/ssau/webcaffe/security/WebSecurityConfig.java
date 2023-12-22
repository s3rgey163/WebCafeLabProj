package ru.ssau.webcaffe.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import ru.ssau.webcaffe.repo.UserRepository;
import ru.ssau.webcaffe.service.DefaultUserDetailService;

import java.util.List;

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
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain newSecurityFilterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> corsConfiguration())
                .sessionManagement(smc -> smc.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)
                ).authorizeHttpRequests(amr -> amr
//                        .requestMatchers(
//                                mvc.pattern((String) SecurityAttributes.SIGN_UP_URLS.getValue()),
//                                mvc.pattern(HttpMethod.OPTIONS, "/**")
//                        ).permitAll()
                                .requestMatchers(
                                        mvc.pattern((String) SecurityAttributes.SIGN_UP_URLS.getValue()),
                                        mvc.pattern(HttpMethod.OPTIONS, "/**")
                                ).permitAll()
                                .requestMatchers(
                                        mvc.pattern("/api/**")
                                ).authenticated().anyRequest().permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exh -> exh.authenticationEntryPoint(jwtEntryPoint))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedOriginPatterns(List.of("*:[*]"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setExposedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(1000L);
        return corsConfiguration;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*");
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(
            DefaultUserDetailService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
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
