package com.remetu.afyasoft.modules.user.http.request;

import com.remetu.afyasoft.classes.DataPageRequestParam;
import com.remetu.afyasoft.modules.user.http.filters.UserFilters;
import com.remetu.afyasoft.modules.user.http.specifications.UserSpec;
import com.remetu.afyasoft.modules.user.models.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.List;

@Data
public class UserDataPageRequestParam extends DataPageRequestParam {
    private UserFilters filters;

    public Specification<User> getSpecification() {
        return UserSpec.filterBy(filters);
    }

    @Override
    public DataPageRequestParam setParameters(HashMap<String, String> parameters) {
        String username = parameters.get("filters[username]");
        filters = new UserFilters(username, List.of(), null, null);
        return super.setParameters(parameters);
    }
}
