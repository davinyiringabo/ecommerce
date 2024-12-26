package com.ecommerce.ecommerce.auth;

import com.ecommerce.ecommerce.dtos.LoginRequest;
import com.ecommerce.ecommerce.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dtos.SignupRequest;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.payload.ApiResponse;
import com.ecommerce.ecommerce.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        if(loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("All credentials are required");
        }
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody SignupRequest signupRequest) {
        User user = new User();
        if (signupRequest.getEmail() == null || signupRequest.getFirstName() == null
                || signupRequest.getLastName() == null || signupRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail(false, "All credentials are required!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail(false, "Email already exists..."));
        } else {
            // Create user account
            user.setEmail(signupRequest.getEmail());
            user.setFirstName(signupRequest.getFirstName());
            user.setLastName(signupRequest.getLastName());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            userRepository.save(user);
        }


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success( true, "User registered successfully", user));
    }
}
