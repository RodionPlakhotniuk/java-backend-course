package org.example.practiceonspringdatajpa.controller;

import org.example.practiceonspringdatajpa.model.Post;
import org.example.practiceonspringdatajpa.model.User;

import java.util.List;

public class UserPostsRequest {
    private User user;
    private List<Post> posts;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}

