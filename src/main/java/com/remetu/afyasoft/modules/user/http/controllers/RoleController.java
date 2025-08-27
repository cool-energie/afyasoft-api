package com.remetu.afyasoft.modules.user.http.controllers;

import com.remetu.afyasoft.modules.user.models.Role;
import com.remetu.afyasoft.modules.user.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<Role> save(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.save(role));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAll(){
        return ResponseEntity.ok(roleService.getAll());
    }
}
