package com.remetu.afyasoft.modules.user.repositories;

import com.remetu.afyasoft.modules.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
    Page<User> findAll(Pageable pageable);
    Page<User> findAll(Specification<User> specification, Pageable pageable);
}
