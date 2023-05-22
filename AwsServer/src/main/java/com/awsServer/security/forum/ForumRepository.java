package com.awsServer.security.forum;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForumRepository extends JpaRepository<Forum, Integer> {
    Optional<Forum> findForumByName(String name);
}
