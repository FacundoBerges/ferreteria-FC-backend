package com.ferreteriafc.api.backend.web.security.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private final Environment environment;
    private Long EXPIRATION_SECONDS;
    private SecretKey SECRET_KEY;
    private String ISSUER;

    @Autowired
    public JwtUtils(Environment environment) {
        this.environment = environment;
        setEnvironmentProperties();
    }

    private void setEnvironmentProperties() {
        String secret = environment.getProperty("jwt.secret", String.class);
        this.EXPIRATION_SECONDS = environment.getProperty("jwt.expiration-seconds", Long.class);
        this.ISSUER = environment.getProperty("jwt.issuer", String.class);

        //! TODO: delete in production
        if (secret == null) {
            secret = UUID.randomUUID().toString();
        }

        if (EXPIRATION_SECONDS == null) {
            EXPIRATION_SECONDS = 60L * 60;
        }

        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        Instant expiration = now.plus(EXPIRATION_SECONDS, ChronoUnit.SECONDS);

        Date issueDate = Date.from(now);
        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .header()
                .type("jwt")
                .and()
                .issuer(ISSUER)
                .issuedAt(issueDate)
                .expiration(expirationDate)
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Jwts.parser()
                    .clockSkewSeconds(10L)
                    .requireIssuer(ISSUER)
                    .requireSubject(userDetails.getUsername())
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
