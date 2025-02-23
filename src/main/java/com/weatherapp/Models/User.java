package com.weatherapp.Models;


import com.weatherapp.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @NotBlank(message = "Email is mandatory")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password can not be empty")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @NotBlank(message = "username can not be empty")
    @Column(nullable = false ,unique = true)
    private String username;


}
