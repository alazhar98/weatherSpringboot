package com.weatherapp.Models;


import com.weatherapp.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate the ID
    private Long id; // Primary key

    @NotBlank(message = "Email can not be empty")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password can not be empty")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Username can not be empty")
    @Column(nullable = false, unique = true)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
