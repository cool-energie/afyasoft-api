package com.remetu.afyasoft.modules.user.services;

import com.remetu.afyasoft.modules.user.http.request.UserDataPageRequestParam;
import com.remetu.afyasoft.modules.user.models.User;
import com.remetu.afyasoft.modules.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Page<User> getPage(UserDataPageRequestParam param) {
        return userRepository.findAll(param.getSpecification(), param.getPageable());
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public void changePassword(UUID id, String newPassword) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresentOrElse(
                value -> {
                    value.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(value);
                },
                () -> {
                    throw new RuntimeException("Missing user!");
                }
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
