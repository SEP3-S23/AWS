package com.awsServer.security.post;

import com.awsServer.security.services.ForumService;
import com.awsServer.security.services.JwtService;
import com.awsServer.security.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<String> createPost(HttpServletRequest request, @RequestBody PostRequest postRequest)
    {
        String token = jwtService.extractTokenFromAuthorizationHeader(request.getHeader("Authorization"));
        String username = jwtService.extractUsername(token);
        postService.createPost(username, postRequest);
        return ResponseEntity.ok("Post successfully created");
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts(HttpServletRequest request)
    {
        String token = jwtService.extractTokenFromAuthorizationHeader(request.getHeader("Authorization"));
        String username = jwtService.extractUsername(token);
        boolean isTokenValid = jwtService.isTokenValid(token, userDetailsService.loadUserByUsername(username));

        if(isTokenValid)
        {
            return ResponseEntity.ok(postService.getAllPosts());
        }
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your session has expired. Please login again");
    }
}
