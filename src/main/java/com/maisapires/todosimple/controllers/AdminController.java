package com.maisapires.todosimple.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin-endpoint")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Acesso concedido ao administrador.");
    }
}
