package com.awsServer.group3.services;

import com.awsServer.group3.forum.Forum;
import com.awsServer.group3.user.Status;
import com.awsServer.group3.user.User;
import com.awsServer.group3.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void reportUser(String userName) {
        Optional<User> userOpt = userRepository.findByUserName(userName);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setStatus(Status.REPORTED);
            userRepository.save(user);
        }
    }

    public void banUser(String userName) {
        Optional<User> userOpt = userRepository.findByUserName(userName);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setStatus(Status.BANNED);
            userRepository.save(user);
        }
    }

    public List<User> getAllReportedUser() {
        return userRepository.findAllByStatus(Status.REPORTED);
    }

    public List<Forum> getSubscribedForum(String username) {
        Optional<User> userOpt = userRepository.findByUserName(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getFollowedForums();
        }
        return null;
    }
}
