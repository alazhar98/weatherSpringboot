package com.weatherapp.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "users") // Explicitly specify the table name for clarity
public class User {
    @NotBlank(message = "Email is mandatory")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Use EnumType.STRING for storing role as a string in the database
    @Column(nullable = false)
    private Role role;

    private String username;


}
