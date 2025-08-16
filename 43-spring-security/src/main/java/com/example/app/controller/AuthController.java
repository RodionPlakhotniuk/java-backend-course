package com.example.app.controller;

import com.example.app.dto.UserRegistrationDto;
import com.example.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String doRegister(@ModelAttribute("user") @Valid UserRegistrationDto dto,
                             BindingResult result) {
        if (result.hasErrors()) return "registration";
        try {
            userService.save(dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("email", "duplicate", ex.getMessage());
            return "registration";
        }
        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
