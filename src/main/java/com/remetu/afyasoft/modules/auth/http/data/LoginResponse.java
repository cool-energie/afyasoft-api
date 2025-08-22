package com.remetu.afyasoft.modules.auth.http.data;

import com.remetu.afyasoft.modules.user.models.User;

public record LoginResponse(User user, String accessToken) {
}
