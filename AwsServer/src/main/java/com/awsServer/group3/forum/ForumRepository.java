package com.awsServer.group3.forum;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForumRepository extends JpaRepository<Forum, Integer> {
    Optional<Forum> findByName(String name);
    Optional<Forum> findForumById(int id);
    @NotNull List<Forum> findAll();
}
