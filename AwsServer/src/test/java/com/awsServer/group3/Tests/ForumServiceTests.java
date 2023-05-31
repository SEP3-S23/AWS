package com.awsServer.group3.Tests;

import com.awsServer.group3.forum.Forum;
import com.awsServer.group3.forum.ForumListDto;
import com.awsServer.group3.forum.ForumRepository;
import com.awsServer.group3.forum.ForumRequest;
import com.awsServer.group3.services.ForumService;
import com.awsServer.group3.user.User;
import com.awsServer.group3.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ForumServiceTests {

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);
    @Mock
    private ForumRepository forumRepository = mock(ForumRepository.class);
    @Mock
    private  ModelMapper modelMapper = mock(ModelMapper.class);
    @Mock
    private ForumService forumService = new ForumService(userRepository, forumRepository, modelMapper);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        forumService = new ForumService(userRepository, forumRepository, modelMapper);
    }

    @Test
    void createForum_NotExistingForum_SuccessfullyCreated() {

        String username = "testUser";
        ForumRequest forumRequest = new ForumRequest(username,"Test Forum", "Test Category", "Test Description");
        User user = new User();
        user.setUserName(username);
        Forum existingForum = new Forum();
        existingForum.setName(forumRequest.getName());

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(forumRepository.findByName(forumRequest.getName())).thenReturn(Optional.empty());
        when(forumRepository.save(any(Forum.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Forum createdForum = forumService.createForum(username, forumRequest);


        assertNotNull(createdForum);
        assertEquals(forumRequest.getName(), createdForum.getName());
        assertEquals(forumRequest.getCategory(), createdForum.getCategory());
        assertEquals(forumRequest.getDescription(), createdForum.getDescription());
        assertEquals(user, createdForum.getUser());

        verify(userRepository).findByUserName(username);
        verify(forumRepository).findByName(forumRequest.getName());
        verify(forumRepository).save(any(Forum.class));
    }

    @Test
    void createForum_ExistingForum_ReturnsNull() {

        String username = "testUser";
        ForumRequest forumRequest = new ForumRequest(username,"Test Forum", "Test Category", "Test Description");
        User user = new User();
        user.setUserName(username);
        Forum existingForum = new Forum();
        existingForum.setName(forumRequest.getName());

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(forumRepository.findByName(forumRequest.getName())).thenReturn(Optional.of(existingForum));


        Forum createdForum = forumService.createForum(username, forumRequest);


        assertNull(createdForum);

        verify(userRepository).findByUserName(username);
        verify(forumRepository).findByName(forumRequest.getName());
        verify(forumRepository, never()).save(any(Forum.class));
    }

    @Test
    void getAllForums_ReturnsListOfForumListDto() {

        List<Forum> forums = new ArrayList<>();
        forums.add(new Forum());
        forums.add(new Forum());
        forums.add(new Forum());
        List<ForumListDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(new ForumListDto());
        expectedDtoList.add(new ForumListDto());
        expectedDtoList.add(new ForumListDto());

        when(forumRepository.findAll()).thenReturn(forums);
        when(modelMapper.map(any(Forum.class), eq(ForumListDto.class))).thenReturn(new ForumListDto());


        List<ForumListDto> actualDtoList = forumService.getAllForums();


        assertNotNull(actualDtoList);
        assertEquals(expectedDtoList.size(), actualDtoList.size());

        verify(forumRepository).findAll();
        verify(modelMapper, times(forums.size())).map(any(Forum.class), eq(ForumListDto.class));
    }

    @Test
    void addForumToUser_UserAndForumExist_ForumAddedToUser() {
        String username = "testUser";
        Integer forumId = 1;

        User user = new User();
        user.setUserName(username);
        Forum forum = new Forum();
        userRepository.save(user);
        user.addForum(forum);
        forum.setId(forumId);

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum));

        forumService.addForumToUser(username, forumId);

        verify(userRepository).save(user);
        assertTrue(user.getFollowedForums().contains(forum));
    }

    @Test
    void addForumToUser_UserDoesNotExist_ForumNotAddedToUser() {
        String username = "testUser";
        Integer forumId = 1;

        when(userRepository.findByUserName(username)).thenReturn(Optional.empty());
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(new Forum()));

        forumService.addForumToUser(username, forumId);

        verify(userRepository, never()).save(any());
    }

    @Test
    void addForumToUser_ForumDoesNotExist_ForumNotAddedToUser() {
        String username = "testUser";
        Integer forumId = 1;

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(new User()));
        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        forumService.addForumToUser(username, forumId);

        verify(userRepository, never()).save(any());
    }

    @Test
    void removeForumFromUser_UserAndForumExist_ForumRemovedFromUser() {
        String username = "testUser";
        Integer forumId = 1;

        User user = new User();
        user.setUserName(username);
        Forum forum = new Forum();
        forum.setId(forumId);
        user.addForum(forum);

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum));

        forumService.removeForumFromUser(username, forumId);

        verify(userRepository).save(user);
        assertFalse(user.getFollowedForums().contains(forum));
    }

}