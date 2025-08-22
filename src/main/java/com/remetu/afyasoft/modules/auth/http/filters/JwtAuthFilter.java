package com.remetu.afyasoft.modules.auth.http.filters;

import com.remetu.afyasoft.modules.auth.services.JwtService;
import com.remetu.afyasoft.services.HttpService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private HttpService httpService;
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if(!path.startsWith("/auth")) {
            String token = httpService.getAuthToken(request);
            if(jwtService.isTokenRevoked(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized Access");
            } else {
                response.setHeader("Access-Control-Expose-Headers", "x-security-token");
                response.setHeader("x-security-token", jwtService.generateRefreshToken(token));
            }
        }
        filterChain.doFilter(request, response);
    }
}
