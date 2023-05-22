package com.awsServer.security.services;

import com.awsServer.security.forum.Forum;
import com.awsServer.security.forum.ForumRepository;
import com.awsServer.security.forum.ForumRequest;
import com.awsServer.security.user.User;
import com.awsServer.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumService {

    private final UserRepository userRepository;
    private final ForumRepository forumRepository;

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
}
