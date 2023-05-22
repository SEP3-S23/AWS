package com.awsServer.security.services;

import com.awsServer.security.auth.AuthenticationRequest;
import com.awsServer.security.auth.AuthenticationResponse;
import com.awsServer.security.auth.RegisterRequest;
import com.awsServer.security.config.JwtService;
import com.awsServer.security.user.Role;
import com.awsServer.security.user.User;
import com.awsServer.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        var user = User.builder()
                .fullName(request.getFullName())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .userName(request.getUserName())
                .role(Role.USER)
                .password(passwordEncoder.encode(request.getPassword())).build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        var user = repository.findByUserName(request.getUserName()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

}
