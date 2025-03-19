package com.ferreteriafc.api.backend.domain.service.implementation;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.*;

import com.ferreteriafc.api.backend.domain.service.IJwtService;

@Service
public class JwtServiceImpl implements IJwtService {

    private final Environment environment;
    private static final String ID = UUID.randomUUID().toString();
    private static Long EXPIRATION_SECONDS;
    private static SecretKey SECRET_KEY;
    private static String ISSUER;
    private static String SECRET;

    @Autowired
    public JwtServiceImpl(Environment environment) {
        this.environment = environment;
        setEnvironmentProperties();
    }

    private void setEnvironmentProperties() {
        SECRET = environment.getProperty("jwt.secret", String.class);
        EXPIRATION_SECONDS = environment.getProperty("jwt.expiration-seconds", Long.class);
        ISSUER = environment.getProperty("jwt.issuer", String.class);

        if (SECRET == null) {
            SECRET = UUID.randomUUID().toString();
        }

        SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        if (EXPIRATION_SECONDS == null) {
            EXPIRATION_SECONDS = 60L * 60;
        }

        if (ISSUER == null) {
            ISSUER = "Development";
        }

        Instant now = Instant.now();
        Date issueDate = Date.from(now);
        Date expirationDate = Date.from(now.plus(EXPIRATION_SECONDS, ChronoUnit.SECONDS));

        return Jwts.builder()
                    .header()
                        .type("jwt")
                        .and()
                    .id(ID)
                    .issuer(ISSUER)
                    .subject(userDetails.getUsername())
                    .issuedAt(issueDate)
                    .notBefore(issueDate)
                    .expiration(expirationDate)
                    .encryptWith(SECRET_KEY, Jwts.ENC.A256CBC_HS512)
                    .compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        JwtParser jwtParser = Jwts.parser()
                                    .clockSkewSeconds(10L)
                                    .requireId(ID)
                                    .requireIssuer(ISSUER)
                                    .requireSubject(userDetails.getUsername())
                                    .requireNotBefore(Date.from(Instant.now()))
                                    .requireExpiration(Date.from(Instant.ofEpochSecond(EXPIRATION_SECONDS)))
                                    .decryptWith(SECRET_KEY)
                                    .build();

        return jwtParser.parseEncryptedClaims(token).getPayload().getSubject().equals(userDetails.getUsername());
    }

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                    .build()
                    .parseEncryptedClaims(token)
                    .getPayload()
                    .getSubject();
    }
}
