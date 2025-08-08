package org.example.practiceonspringdatajpa.repository;

import org.example.practiceonspringdatajpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);

    List<User> findByEmailEndingWith(String domain);
}

