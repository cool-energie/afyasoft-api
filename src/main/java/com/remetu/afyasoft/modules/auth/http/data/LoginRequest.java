package com.remetu.afyasoft.modules.auth.http.data;

public record LoginRequest(String username, String password, boolean rememberMe) {
}
