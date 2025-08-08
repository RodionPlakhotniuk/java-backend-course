package org.example.practiceonspringdatajpa.service;

import org.example.practiceonspringdatajpa.model.Post;
import org.example.practiceonspringdatajpa.model.User;
import org.example.practiceonspringdatajpa.repository.PostRepository;
import org.example.practiceonspringdatajpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserPostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public User createUserWithPosts(User user, List<Post> posts) {
        user.setPosts(null);
        User savedUser = userRepository.save(user);

        for (Post post : posts) {
            if (post.getTitle() == null || post.getTitle().trim().isEmpty()) {
                throw new RuntimeException("Post title cannot be null or empty");
            }

            post.setUser(savedUser);
            postRepository.save(post);
        }

        return savedUser;
    }

    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> getUsersByEmailDomain(String domain) {
        return userRepository.findByEmailEndingWith(domain);
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }
}
