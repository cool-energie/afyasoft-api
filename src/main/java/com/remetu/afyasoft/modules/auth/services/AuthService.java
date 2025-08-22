package com.remetu.afyasoft.modules.auth.services;

import com.remetu.afyasoft.modules.auth.http.data.LoginRequest;
import com.remetu.afyasoft.modules.auth.http.data.LoginResponse;
import com.remetu.afyasoft.modules.user.models.User;
import com.remetu.afyasoft.modules.user.repositories.UserRepository;
import com.remetu.afyasoft.services.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public LoginResponse login(String username, String password) {
        try {
            Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            authenticationManager.authenticate(authentication);
            User user = userRepository.findByUsername(username);
            String accessToken = jwtService.generateAccessToken(username);
            return new LoginResponse(user, accessToken);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout(String authorization) {
        try {
            if(authorization.length() < 7) return;
            String token = authorization.substring(7);
            if(!token.isBlank() && !jwtService.isExpired(token)) {
                jwtService.revokeToken(token);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}
