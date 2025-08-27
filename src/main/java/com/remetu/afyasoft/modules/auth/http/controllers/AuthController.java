package com.remetu.afyasoft.modules.auth.http.controllers;

import com.remetu.afyasoft.modules.auth.http.data.LoginRequest;
import com.remetu.afyasoft.modules.auth.http.data.LoginResponse;
import com.remetu.afyasoft.modules.auth.services.AuthService;
import com.remetu.afyasoft.services.HttpService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private HttpService httpService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request.username(), request.password()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout(httpService.getAuthToken(request));
        return ResponseEntity.ok("User disconnected");
    }
}
