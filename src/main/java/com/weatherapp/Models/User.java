package com.weatherapp.Models;


import com.weatherapp.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Entity

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

    // Private constructor for the builder
    private User(UserBuilder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.userName = builder.userName;
        this.role = builder.role;
    }

    // Default constructor (needed for JPA)
    public User() {
    }

    // Builder class
    public static class UserBuilder {
        private Long id;
        private String email;
        private String password;
        private String userName;
        private Role role;

        public UserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setRole(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    // Static method to get a new builder instance
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
