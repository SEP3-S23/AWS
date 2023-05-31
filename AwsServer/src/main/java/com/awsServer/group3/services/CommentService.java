package com.awsServer.group3.services;

import com.awsServer.group3.comment.Comment;
import com.awsServer.group3.comment.CommentRepository;
import com.awsServer.group3.comment.CommentRequest;
import com.awsServer.group3.post.Post;
import com.awsServer.group3.post.PostRepository;
import com.awsServer.group3.services.PostService;
import com.awsServer.group3.user.User;
import com.awsServer.group3.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private UserRepository userRepository;

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    public void createComment(String username, CommentRequest body) throws Exception {
        Optional<User> userOpt = userRepository.findByUserName(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            Optional<Post> postOpt = postRepository.findByTitle(body.getPostName());

            if (postOpt.isPresent()) {

                Post post = postOpt.get();

                Comment comment = Comment.builder()
                        .text(body.getText())
                        .post(post)
                        .user(user)
                        .build();

                commentRepository.save(comment);

            } else {
                throw new Exception("Post not found");
            }
        } else {
            throw new Exception("User not found");
        }
    }
}
