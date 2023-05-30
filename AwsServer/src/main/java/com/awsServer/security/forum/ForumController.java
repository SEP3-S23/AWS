package com.awsServer.security.forum;

import com.awsServer.security.services.ForumService;
import com.awsServer.security.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/forums")
@RequiredArgsConstructor
public class ForumController {
    private final ForumService forumService;
    private final JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<String> createForum(HttpServletRequest request,@RequestBody ForumRequest forumRequest) {
        String token = jwtService.extractTokenFromAuthorizationHeader(request.getHeader("Authorization"));
        String username = jwtService.extractUsername(token);
        if(forumService.createForum(username, forumRequest)==null)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Forum's name already exists. Please select another name.");
        }
        return ResponseEntity.ok("Forum successfully created.");

    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getForumByName(@PathVariable("name") String name) {
            ForumReturned forum = forumService.getForumByName(name);
            return ResponseEntity.ok(forum);
    }

    @PostMapping("/subscribe/{forumId}")
    public ResponseEntity<Void> addForumToUser(HttpServletRequest request,@PathVariable Integer forumId)
    {
        String token = jwtService.extractTokenFromAuthorizationHeader(request.getHeader("Authorization"));
        String username = jwtService.extractUsername(token);
        forumService.addForumToUser(username, forumId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unsubscribe/{forumId}")
    public ResponseEntity<Void> deleteForumToUser(HttpServletRequest request,@PathVariable Integer forumId)
    {
        String token = jwtService.extractTokenFromAuthorizationHeader(request.getHeader("Authorization"));
        String username = jwtService.extractUsername(token);
        forumService.removeForumFromUser(username, forumId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllForums()
    {
        return ResponseEntity.ok(forumService.getAllForums());
    }
}
