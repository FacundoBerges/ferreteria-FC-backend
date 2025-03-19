package com.ferreteriafc.api.backend.web.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ferreteriafc.api.backend.domain.service.IJwtService;
import com.ferreteriafc.api.backend.domain.service.IUserService;
import com.ferreteriafc.api.backend.web.exception.InvalidJwtException;
import com.ferreteriafc.api.backend.web.exception.MissingAuthenticationHeaderException;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final IUserService userService;

    @Autowired
    public JwtAuthenticationFilter(IJwtService jwtService, IUserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null)
            throw new MissingAuthenticationHeaderException("Authorization header not found in request.");

        if (!authorization.startsWith("Bearer "))
            throw new NotFoundException("Authorization header is invalid.");

        String jsonWebToken = authorization.replace("Bearer ", "");
        String username = jwtService.getUsernameFromToken(jsonWebToken);

        UserDetails userDetails = userService.loadUserByUsername(username);

        if (!jwtService.validateToken(jsonWebToken, userDetails))
            throw new InvalidJwtException("Invalid JWToken.");

        Authentication auth =
            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
