package com.maisapires.todosimple.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.maisapires.todosimple.models.User;
import com.maisapires.todosimple.models.dto.UserLoginDTO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 864_000_000;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
            String token = generateToken(authentication);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    private String generateToken(Authentication authentication) {
        String username = authentication.getName();
        String role = ((User) authentication.getPrincipal()).getRole(); // Verifique se isso retorna "ROLE_ADMIN"
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY);
        return builder.compact();
    }

}
