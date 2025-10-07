package com.remetu.afyasoft.modules.user.http.controllers;

import com.remetu.afyasoft.classes.DataPageRequestParam;
import com.remetu.afyasoft.modules.user.http.data.ChangePasswordRequest;
import com.remetu.afyasoft.modules.user.http.request.UserDataPageRequestParam;
import com.remetu.afyasoft.modules.user.models.User;
import com.remetu.afyasoft.modules.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@RequestParam HashMap<String, String> param) {
           return ResponseEntity.ok(userService.getPage(new UserDataPageRequestParam(param)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam UUID id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("User Deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Something went wrong !");
        }
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            userService.changePassword(request.id(), request.newPassword());
            return ResponseEntity.ok("Password Modified");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Something went wrong !");
        }
    }
}
