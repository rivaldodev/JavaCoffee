package com.coffee.tracker.controller;

import com.coffee.tracker.dto.LoginRequest;
import com.coffee.tracker.dto.LoginResponse;
import com.coffee.tracker.dto.UserRequest;
import com.coffee.tracker.model.User;
import com.coffee.tracker.service.AuthService;
import com.coffee.tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService; private final AuthService authService;
    public AuthController(UserService userService, AuthService authService){ this.userService=userService; this.authService=authService; }
    @PostMapping("/users") public ResponseEntity<?> register(@Valid @RequestBody UserRequest request){ User user = userService.create(request); return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com id="+user.getId()); }
    @PostMapping("/login") public LoginResponse login(@Valid @RequestBody LoginRequest request){ return authService.login(request); }
}
