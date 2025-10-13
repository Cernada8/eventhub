package com.example.eventhub.api.auth;

import com.example.eventhub.api.auth.dto.LoginRequest;
import com.example.eventhub.api.auth.dto.LoginResponse;
import com.example.eventhub.api.auth.dto.RegisterRequest;
import com.example.eventhub.api.auth.dto.RegisterResponse;
import com.example.eventhub.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest req) {
        RegisterResponse res = authService.register(req);

        return ResponseEntity.status(200).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        LoginResponse res = authService.login(req);
        
        return ResponseEntity.status(200).body(res);
    }

}