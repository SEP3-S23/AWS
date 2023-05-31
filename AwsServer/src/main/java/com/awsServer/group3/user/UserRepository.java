package com.awsServer.group3.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String username);
    Optional<User> findUserByEmail(String email);

    List<User> findAllByStatus(Status status);

}
