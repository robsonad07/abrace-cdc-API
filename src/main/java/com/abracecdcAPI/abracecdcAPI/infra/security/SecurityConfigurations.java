package com.abracecdcAPI.abracecdcAPI.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers( "/organizer/**").permitAll()
                        .requestMatchers( "/registers-action/**").permitAll()
                        .requestMatchers( "/donation-action/**").permitAll()
                        .requestMatchers( "/action/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/address").permitAll()
                        .requestMatchers(HttpMethod.GET, "/address").permitAll()
                        .requestMatchers(HttpMethod.GET, "/address/**").permitAll()                        .requestMatchers(HttpMethod.PUT, "/api/address/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/address/**").permitAll()
                        .requestMatchers( "/categories/**").permitAll()
                        .requestMatchers( "/organizer/delete/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register-user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/**").permitAll()                        .requestMatchers(HttpMethod.DELETE, "/user").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/registers").permitAll()
                        .requestMatchers(HttpMethod.GET, "/register/**").permitAll()                        .requestMatchers(HttpMethod.PUT, "/register/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/register/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/events").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events/** ").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/events/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/events/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/donation-event").permitAll()
                        .requestMatchers(HttpMethod.GET, "/donation-events").permitAll()
                        .requestMatchers(HttpMethod.GET, "/donation-event/**").permitAll()                        .requestMatchers(HttpMethod.PUT, "/donation-event/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/donation-event/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
