package com.example.app.service;

import com.example.app.dto.UserRegistrationDto;
import com.example.app.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);


    User findByEmail(String email);
}
