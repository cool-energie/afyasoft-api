package com.remetu.afyasoft.modules.user.http.data;

import java.util.UUID;

public record ChangePasswordRequest(UUID id, String newPassword) {
}
