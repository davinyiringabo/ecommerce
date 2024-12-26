package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.dtos.LoginRequest;
import com.ecommerce.ecommerce.payload.JwtResponse;
import com.ecommerce.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public JwtResponse login(LoginRequest loginRequest) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginRequest.getEmail(), loginRequest.getPassword()));

                var user = userRepository.findByEmail(loginRequest.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
                var token = jwtService.generateToken(user);
                return JwtResponse.builder()
                                .token(token)
                                .message("User Logged in successfully")
                                .build();
        }
}