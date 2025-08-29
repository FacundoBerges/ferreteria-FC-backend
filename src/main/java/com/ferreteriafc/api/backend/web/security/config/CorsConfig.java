package com.ferreteriafc.api.backend.web.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    private final Environment environment;

    @Autowired
    public CorsConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "OPTIONS");

        String origin = environment.getProperty("cors.origin");
        if (origin == null || origin.isEmpty()) {
            origin = "http://localhost:4200";
        }

        corsConfiguration.setAllowedOrigins(List.of(origin));
        corsConfiguration.setAllowedMethods(allowedMethods);
        corsConfiguration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return corsConfigurationSource;
    }

}
