package com.weatherapp.DTOs;

import lombok.Data;

@Data
public class SignInRequestDTO {

    String Username;
    String password;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    String Password;
}
