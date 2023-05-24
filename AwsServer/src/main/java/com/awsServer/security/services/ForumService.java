package com.awsServer.security.services;

import com.awsServer.security.forum.*;
import com.awsServer.security.post.Post;
import com.awsServer.security.post.PostReturned;
import com.awsServer.security.user.User;
import com.awsServer.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForumService {

    private final UserRepository userRepository;
    private final ForumRepository forumRepository;
    private final ModelMapper modelMapper;

    public Forum createForum(String username, ForumRequest forumRequest)
    {
        Optional<User> createdBy = userRepository.findByUserName(username);
        Optional<Forum> isForumExisting = forumRepository.findForumByName(forumRequest.getName());

        if(isForumExisting.isPresent())
        {
            return null;
        }

        Forum forum = new Forum();
        forum.setUser(createdBy.orElseThrow());
        forum.setName(forumRequest.getName());
        forum.setCategory(forumRequest.getCategory());
        forum.setDescription(forumRequest.getDescription());

        Date creationDate = new Date();
        forum.setTimeCreation(creationDate);

        return forumRepository.save(forum);
    }

    public List<ForumListDto> getAllForums()
    {
        List<Forum> forums = forumRepository.findAll();
        return forums.stream().map(forum -> modelMapper.map(forum, ForumListDto.class)).collect(Collectors.toList());
    }

    public ForumReturned getForumByName(String name) {
        Optional<Forum> forum = forumRepository.findForumByName(name);
        return forum.map(this::convertToForumDto).orElse(null);
}

    private ForumReturned convertToForumDto(Forum forum) {
        ForumReturned forumDto = modelMapper.map(forum, ForumReturned.class);
        List<PostReturned> postDtos = forum.getPosts().stream()
                .map(this::convertToPostDto)
                .collect(Collectors.toList());
        forumDto.setPosts(postDtos);
        return forumDto;
    }

    private PostReturned convertToPostDto(Post post) {
        PostReturned postDto = modelMapper.map(post, PostReturned.class);
        postDto.setUsername(post.getUser().getUsername());
        return postDto;
    }
}
