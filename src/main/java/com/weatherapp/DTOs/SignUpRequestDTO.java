package com.weatherapp.DTOs;

import com.weatherapp.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpRequestDTO {
    @NotBlank(message = "Username is required")
   String username;

    @NotBlank(message = "Password is required")
     String password;

    @Email(message = "Invalid email format")
    String email;

    @NotNull(message = "Role is required")
    Role role;
}
