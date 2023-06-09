package com.awsServer.group3.auth;

import com.awsServer.group3.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        try {
            AuthenticationResponse response = service.register(request);
            return ResponseEntity.ok(response); // Return the token as the response body
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        try {
            AuthenticationResponse response = service.authenticate(request);
            System.out.println(response);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }
}
