package com.ferreteriafc.api.backend.web.security.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ferreteriafc.api.backend.domain.service.IUserService;

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
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jsonWebToken = authorization.replace("Bearer ", "");
        String username;

        try {
            username = jwtUtils.getUsernameFromToken(jsonWebToken);
        } catch (Exception e) {
            username = null;
        }

        if (username == null || SecurityContextHolder.getContext().getAuthentication() == null) {
            doFilterInternal(request, response, filterChain);
            return;
        }

        UserDetails userDetails = userService.loadUserByUsername(username);

        if (!jwtUtils.validateToken(jsonWebToken, userDetails)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            authentication.setDetails(authentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
