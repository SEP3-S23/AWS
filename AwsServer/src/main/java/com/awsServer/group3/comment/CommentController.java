package com.awsServer.group3.comment;

import com.awsServer.group3.services.CommentService;
import com.awsServer.group3.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    private final JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(HttpServletRequest request, @RequestBody CommentRequest body) {
        String token = jwtService.extractTokenFromAuthorizationHeader(request.getHeader("Authorization"));
        String username = jwtService.extractUsername(token);

        try {
            commentService.createComment(username, body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
