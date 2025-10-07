package com.remetu.afyasoft.modules.user.http.filters;

import com.remetu.afyasoft.modules.user.models.Role;

import java.util.Date;
import java.util.List;

public record UserFilters(String username, List<Role> roles, Boolean enabled, List<Date> createdAt) {
}
