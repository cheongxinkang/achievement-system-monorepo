package com.xk.achievement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtSessionFilter jwtSessionFilter;

    public SecurityConfig(JwtSessionFilter jwtSessionFilter) {
        this.jwtSessionFilter = jwtSessionFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/authenticate", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtSessionFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(login -> login
                .loginPage("/login")
                .permitAll()
            );
        return http.build();
    }
}
