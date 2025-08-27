package com.remetu.afyasoft.modules.user.repositories;

import com.remetu.afyasoft.modules.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    Page<User> findAll(Pageable pageable);
}
