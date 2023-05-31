package com.awsServer.group3.Tests;

import com.awsServer.group3.forum.Forum;
import com.awsServer.group3.forum.ForumRepository;
import com.awsServer.group3.post.Post;
import com.awsServer.group3.post.PostRepository;
import com.awsServer.group3.post.PostRequest;
import com.awsServer.group3.services.PostService;
import com.awsServer.group3.user.User;
import com.awsServer.group3.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ForumRepository forumRepository;
    @Mock
    private  PostService postService = new PostService(postRepository, userRepository, forumRepository);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postRepository, userRepository, forumRepository);
    }

    @Test
    void createPost_UserAndForumExist_PostCreated() {
        String username = "testUser";
        Integer forumId = 1;

        User user = new User();
        user.setUserName(username);
        Forum forum = new Forum();
        forum.setFollowedUsers(new ArrayList<>());
        forum.setId(forumId);
        forum.setName("name");
        user.addForum(forum);
        forum.getFollowedUsers().add(user);

        PostRequest postRequest = new PostRequest();
        postRequest.setForumName("name");
        postRequest.setTitle("Test Post");
        postRequest.setBody("This is a test post");

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(forumRepository.findByName("name")).thenReturn(Optional.of(forum));
        when(postRepository.save(Mockito.any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            savedPost.setId(1);
            return savedPost;
        });

        Post createdPost = postService.createPost(username, postRequest);

        assertNotNull(createdPost);
        assertEquals(1, createdPost.getId());
        assertEquals(forum, createdPost.getForum());
        assertEquals(user, createdPost.getUser());
        assertEquals("Test Post", createdPost.getTitle());
        assertEquals("This is a test post", createdPost.getBody());
        assertNotNull(createdPost.getDate());
    }

    @Test
    void createPost_UserDoesNotFollowForum_ReturnsNull() {
        String username = "testUser";
        Integer forumId = 1;

        User user = new User();
        user.setUserName(username);
        Forum forum = new Forum();
        forum.setFollowedUsers(new ArrayList<>());
        forum.setId(forumId);
        forum.setName("name");

        PostRequest postRequest = new PostRequest();
        postRequest.setForumName("name");
        postRequest.setTitle("Test Post");
        postRequest.setBody("This is a test post");

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(forumRepository.findByName("name")).thenReturn(Optional.of(forum));

        Post createdPost = postService.createPost(username, postRequest);

        assertNull(createdPost);
    }
}