package com.remetu.afyasoft.modules.user.http.specifications;

import com.remetu.afyasoft.modules.user.http.filters.UserFilters;
import com.remetu.afyasoft.modules.user.models.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpec {
    public static Specification<User> filterBy(UserFilters filter) {
        return isUsernameLike(filter.username());
    }

    private static Specification<User> isUsernameLike(String username) {
        return (root, query, criteriaBuilder) -> username.isBlank() ? criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("username"), "%"+ username +"%");
    }
}
