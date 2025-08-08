package org.example.practiceonspringdatajpa.controller;

import org.example.practiceonspringdatajpa.model.Post;
import org.example.practiceonspringdatajpa.model.User;
import org.example.practiceonspringdatajpa.service.UserPostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserPostController {

    private final UserPostService service;

    public UserPostController(UserPostService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public User createUserWithPosts(@RequestBody UserPostsRequest request) {
        return service.createUserWithPosts(request.getUser(), request.getPosts());
    }

    @GetMapping("/users/name/{name}")
    public List<User> getUsersByName(@PathVariable String name) {
        return service.getUsersByName(name);
    }

    @GetMapping("/users/domain")
    public List<User> getUsersByDomain(@RequestParam String domain) {
        return service.getUsersByEmailDomain(domain);
    }

    @GetMapping("/posts/user/{userId}")
    public List<Post> getPostsByUserId(@PathVariable Long userId) {
        return service.getPostsByUserId(userId);
    }
}
