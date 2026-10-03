package com.petclinic.security.service;
import com.petclinic.security.util.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petclinic.security.dto.AuthResponse;
import com.petclinic.security.dto.LoginRequest;
import com.petclinic.security.dto.RegisterRequest;
import com.petclinic.security.exception.UserAlreadyExistsException;
import com.petclinic.security.model.User;
import com.petclinic.security.repository.UserRepository;

@Service
public class AuthService {

    private static final Logger log =
            LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

this.userRepository = userRepository;
this.passwordEncoder = passwordEncoder;
this.jwtService = jwtService;
}

    public String register(RegisterRequest request) {

        log.info("Register request received for username: {}",
                request.getUsername());

        if (userRepository.existsByUsername(
                request.getUsername())) {

            throw new UserAlreadyExistsException(
                    "Username already exists");
        }

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new UserAlreadyExistsException(
                    "Email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        log.info("User registered successfully: {}",
                request.getUsername());

        return "User Registered Successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BadCredentialsException(
                    "Invalid username or password");
        }

        AuthResponse response = new AuthResponse();

        response.setMessage("Login Successful");

        response.setToken(
                jwtService.generateToken(user));
        return response;
    }
}