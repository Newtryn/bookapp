package com.emrestaj.bookapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig {

    // Define login credentials
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("emre")
            .password("1234")
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    // Define security rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for testing
            .authorizeHttpRequests(auth -> auth
                // Allow anonymous access to Swagger UI & related resources
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-resources/**",
                    "/configuration/**",
                    "/webjars/**"
                ).permitAll()
                // Require authentication for everything else (including API calls)
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic Auth

        return http.build();
    }
}
