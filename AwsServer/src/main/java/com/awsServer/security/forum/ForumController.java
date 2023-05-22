package com.awsServer.security.forum;

import com.awsServer.security.services.ForumService;
import com.awsServer.security.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/forums")
@RequiredArgsConstructor
public class ForumController {
    private final ForumService forumService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<String> createForum(HttpServletRequest request,@RequestBody ForumRequest forumRequest) {
        String token = jwtService.extractTokenFromAuthorizationHeader(request.getHeader("Authorization"));
        String username = jwtService.extractUsername(token);
        boolean isTokenValid = jwtService.isTokenValid(token, userDetailsService.loadUserByUsername(username));

        if (isTokenValid) {
            if (forumService.createForum(username, forumRequest) == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("The forum name already exists. Please choose another name.");
            } else return ResponseEntity.ok("Forum successfully created.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your session has expired. Please login again.");
        }
    }
}
