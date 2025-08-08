package org.example.practiceonspringdatajpa.repository;

import org.example.practiceonspringdatajpa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
}