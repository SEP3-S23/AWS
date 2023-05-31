package com.awsServer.group3.post;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @NotNull List<Post> findAllBy();

    Optional<Post> findByTitle(String name);
}
