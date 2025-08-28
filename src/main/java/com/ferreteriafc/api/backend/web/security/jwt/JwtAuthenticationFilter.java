package com.ferreteriafc.api.backend.web.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ferreteriafc.api.backend.domain.service.IUserService;
import com.ferreteriafc.api.backend.web.exception.InvalidJwtException;
import com.ferreteriafc.api.backend.web.exception.MissingAuthenticationHeaderException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final IUserService userService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtils jwtUtils, IUserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || authorization.isEmpty())
            throw new MissingAuthenticationHeaderException("Authorization header not found in request.");

        if (!authorization.startsWith("Bearer "))
            throw new MissingAuthenticationHeaderException("Authorization header is invalid.");

        String jsonWebToken = authorization.replace("Bearer ", "");
        String username = jwtUtils.getUsernameFromToken(jsonWebToken);

        if (username == null)
            throw new InvalidJwtException("Invalid token.");

        UserDetails userDetails = userService.loadUserByUsername(username);

        if (!jwtUtils.validateToken(jsonWebToken, userDetails))
            throw new InvalidJwtException("Invalid JWToken.");

        Authentication authentication = new UsernamePasswordAuthenticationToken(  userDetails.getUsername(),
                                                                        userDetails.getPassword(),
                                                                        userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}
