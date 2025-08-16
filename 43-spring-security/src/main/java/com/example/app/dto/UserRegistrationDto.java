package com.example.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {
    @NotBlank(message = "Вкажіть ім'я")
    private String name;

    @NotBlank(message = "Вкажіть телефон")
    private String phone;

    @Email(message = "Некоректний email")
    @NotBlank(message = "Вкажіть email")
    private String email;

    @Size(min = 4, message = "Пароль має містити щонайменше 4 символи")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
