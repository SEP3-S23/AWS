package com.awsServer.group3.services;

import com.awsServer.group3.forum.Forum;
import com.awsServer.group3.forum.ForumRepository;
import com.awsServer.group3.post.Post;
import com.awsServer.group3.post.PostRepository;
import com.awsServer.group3.post.PostRequest;
import com.awsServer.group3.user.User;
import com.awsServer.group3.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

        Forum forum = existingForum.get();
        User user = createdBy.get();
        if(!forum.getFollowedUsers().contains(user))
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

    public List<Post> getAllPosts()
    {
        return postRepository.findAll();
    }

    public void addLike(String title) throws Exception {
        Optional<Post> postOpt = postRepository.findByTitle(title);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();

            Integer likes = post.getLike();
            post.setLike(likes + 1);

            postRepository.save(post);
        } else {
            throw new Exception("Post not found");
        }
    }
}
