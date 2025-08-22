package com.coffee.tracker.service;

import com.coffee.tracker.dto.LoginRequest;
import com.coffee.tracker.dto.LoginResponse;
import com.coffee.tracker.exception.ResourceNotFoundException;
import com.coffee.tracker.model.User;
import com.coffee.tracker.repository.UserRepository;
import com.coffee.tracker.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository; private final PasswordEncoder passwordEncoder; private final JwtTokenProvider tokenProvider;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider){ this.userRepository=userRepository; this.passwordEncoder=passwordEncoder; this.tokenProvider=tokenProvider; }
    public LoginResponse login(LoginRequest request){ User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado")); if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new IllegalArgumentException("Credenciais inválidas"); String token = tokenProvider.generateToken(user.getId(), user.getEmail()); return new LoginResponse(token); }
}
