package com.awsServer.group3.services;

import com.awsServer.group3.auth.AuthenticationRequest;
import com.awsServer.group3.auth.AuthenticationResponse;
import com.awsServer.group3.auth.RegisterRequest;
import com.awsServer.group3.user.Role;
import com.awsServer.group3.user.Status;
import com.awsServer.group3.user.User;
import com.awsServer.group3.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        if (repository.findByUserName(request.getUserName()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (repository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        var user = User.builder()
                .fullName(request.getFullName())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .userName(request.getUserName())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .password(passwordEncoder.encode(request.getPassword())).build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        var user = repository.findByUserName(request.getUserName()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

}
