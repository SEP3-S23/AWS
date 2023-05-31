package com.awsServer.group3.user;

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
}
