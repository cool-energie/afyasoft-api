package com.remetu.afyasoft.classes;

import com.remetu.afyasoft.helpers.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class DataPageRequestParam {
    private int page;
    private int itemsPerPage;
    private String search;
    private String sortBy;

    public DataPageRequestParam setParameters(HashMap<String, String> parameters) {
        this.page = Utils.isNull(parameters.get("options[page]")) ? Integer.getInteger(parameters.get("options[page]")) : 0;
        this.itemsPerPage = Utils.isNull(parameters.get("options[itemsPerPage]")) ? Integer.getInteger(parameters.get("options[itemsPerPage]")) : 0;
        this.search = parameters.get("options[search]");
        this.sortBy = parameters.get("options[sortBy]");
        return this;
    }

    public DataPageRequestParam get() {
        return this;
    }

    public Sort getSort() {
        if(sortBy == null || sortBy.isBlank()) return null;
        return Sort.by(Arrays.stream(sortBy.split(",")).map(this::toOrder).toList());
    }

    private Sort.Order toOrder(String sortStr) {
        String[] sortParts = sortStr.split(":");
        return new Sort.Order(Objects.equals(sortParts[1], "asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortParts[0]);
    }

    public Pageable getPageable() {
        return getSort() != null ? PageRequest.of(page - 1, itemsPerPage, getSort()) : PageRequest.of(page - 1, itemsPerPage);
    }
}
