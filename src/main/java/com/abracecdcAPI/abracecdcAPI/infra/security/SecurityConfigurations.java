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
                        .requestMatchers(HttpMethod.POST, "/address").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/address").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/address/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/address/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/address/**").hasRole("ADMIN")
                        .requestMatchers( "/categories/**").permitAll()
                        .requestMatchers( "/organizer/delete/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register-user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/registers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/register/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/register/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/register/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/event").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/events").permitAll()
                        .requestMatchers(HttpMethod.GET, "/event/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/event/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/event/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/donation-event").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/donation-events").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/donation-event/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/donation-event/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/donation-event/**").hasRole("ADMIN")
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
