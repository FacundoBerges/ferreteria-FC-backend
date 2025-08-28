package com.ferreteriafc.api.backend.web.config;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorizationManagerRequestMatcherRegistry) ->
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/auth/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**", "/brands/**", "/categories/**", "/images/**")
                        .permitAll()
                        .requestMatchers("/users/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/products/**", "/brands/**", "/categories/**", "/images/**")
                        .authenticated()
                        .requestMatchers(HttpMethod.PUT, "/products/**", "/brands/**", "/categories/**", "/images/**")
                        .authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/products/**", "/brands/**", "/categories/**", "/images/**")
                        .authenticated()
                        .anyRequest()
                        .authenticated())
            .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

}
