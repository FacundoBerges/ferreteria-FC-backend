package com.ferreteriafc.api.backend.web.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ferreteriafc.api.backend.web.security.jwt.JwtAuthenticationEntryPoint;
import com.ferreteriafc.api.backend.web.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint)
            throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorizationManagerRequestMatcherRegistry) ->
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/auth/login", "/auth/register")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**", "/brands/**", "/categories/**", "/images/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, "/auth/change-password/**")
                        .authenticated()
                        .requestMatchers(HttpMethod.POST, "/products/**", "/brands/**", "/categories/**", "/images/**")
                        .authenticated()
                        .requestMatchers(HttpMethod.PUT, "/products/**", "/brands/**", "/categories/**", "/images/**")
                        .authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/products/**", "/brands/**", "/categories/**", "/images/**")
                        .authenticated()
                        .requestMatchers("/users/**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(handler -> handler.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        return httpSecurity.build();
    }

}
