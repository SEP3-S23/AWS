package com.awsServer.security.Tests;

import com.awsServer.security.auth.AuthenticationRequest;
import com.awsServer.security.auth.AuthenticationResponse;
import com.awsServer.security.auth.RegisterRequest;
import com.awsServer.security.services.AuthenticationService;
import com.awsServer.security.services.JwtService;
import com.awsServer.security.user.Role;
import com.awsServer.security.user.User;
import com.awsServer.security.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationService(repository, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    void register_ValidRequest_SuccessfulRegistration() {

        RegisterRequest request = new RegisterRequest("Test Name", "test@example.com", "testUsername", "password", "2002-21-06");
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .userName(request.getUserName())
                .birthDate(request.getBirthDate())
                .password("hashedPassword")
                .role(Role.USER)
                .build();

        //No user with that Username exists in the repository
        when(repository.findByUserName(request.getUserName())).thenReturn(Optional.empty());
        //No user with that email exists in the repository
        when(repository.findUserByEmail(request.getEmail())).thenReturn(Optional.empty());
        //This simulates the encoded password
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");
        //This simulates the generated token
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        //It captures the user saved in the repository.
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);


        AuthenticationResponse response = authenticationService.register(request);

        //An assertion is made to ensure that the response object returned by the register() method is not null.
        assertNotNull(response);
        //Another assertion is made to verify that the token field of the response object is equal to the string "jwtToken".
        assertEquals("jwtToken", response.getToken());



        //This verifies that the repository.save() method was called exactly
        // once and captures the User object passed as an argument during the method invocation.
        verify(repository).save(userCaptor.capture());
        verify(jwtService).generateToken(any(User.class));

        User capturedUser = userCaptor.getValue();
        assertEquals(user.getFullName(), capturedUser.getFullName());
        assertEquals(user.getEmail(), capturedUser.getEmail());
        assertEquals(user.getUsername(), capturedUser.getUsername());
        assertEquals(user.getBirthDate(), capturedUser.getBirthDate());
        assertEquals(user.getRole(),capturedUser.getRole());
        assertEquals(user.getPassword(),capturedUser.getPassword());
    }

    @Test
    void register_ExistingUsername_ExceptionThrown() {

        RegisterRequest request = new RegisterRequest("Test Name", "test@example.com", "testUsername", "password", "2002-21-06");

        when(repository.findByUserName(request.getUserName())).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> authenticationService.register(request));

        verify(repository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    void register_ExistingEmail_ExceptionThrown() {

        RegisterRequest request = new RegisterRequest("Test Name", "test@example.com", "testUsername", "password", "2002-21-06");

        when(repository.findByUserName(request.getUserName())).thenReturn(Optional.empty());
        when(repository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> authenticationService.register(request));

        verify(repository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    void authenticate_ValidRequest_SuccessfulAuthentication() {
        //Arrange
        AuthenticationRequest request = new AuthenticationRequest("testUsername", "password");
        User user = User.builder()
                .userName(request.getUserName())
                .build();

        //Act
        when(repository.findByUserName(request.getUserName())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(user);
    }

    @Test
    void authenticate_InvalidCredentials_ExceptionThrown() {
        AuthenticationRequest request = new AuthenticationRequest("testUsername", "password");

        when(repository.findByUserName(request.getUserName())).thenReturn(Optional.empty());
        doThrow(AuthenticationServiceException.class).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(IllegalArgumentException.class, () -> authenticationService.authenticate(request));

        verify(jwtService, never()).generateToken(any(User.class));
    }
}