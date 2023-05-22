package com.awsServer.security.services;

import com.awsServer.security.forum.Forum;
import com.awsServer.security.forum.ForumRepository;
import com.awsServer.security.post.Post;
import com.awsServer.security.post.PostRepository;
import com.awsServer.security.post.PostRequest;
import com.awsServer.security.user.User;
import com.awsServer.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ForumRepository forumRepository;

    public Post createPost(String username, PostRequest postRequest)
    {
        Optional<User> createdBy = userRepository.findByUserName(username);
        Optional<Forum> existingForum = forumRepository.findForumByName(postRequest.getForumName());

        if(existingForum.isEmpty())
        {
            return null;
        }

        Post newPost = new Post();

        Date creationDate = new Date();

        newPost.setForum(existingForum.orElseThrow());
        newPost.setDate(creationDate);
        newPost.setUser(createdBy.orElseThrow());
        newPost.setTitle(postRequest.getTitle());
        newPost.setBody(postRequest.getBody());

        return postRepository.save(newPost);
    }
}
