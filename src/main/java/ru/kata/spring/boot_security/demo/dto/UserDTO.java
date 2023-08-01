package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    private long id;

    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;

    @Size(min = 2, max = 30, message = "Lastname must be between 2 and 30 characters")
    private String lastname;

    @NotEmpty(message = "The field must not be empty")
    @Email(message = "Email must be valid")
    private String email;

    @Min(value = 0, message = "Age must be between 0 and 100 years old")
    private byte age;

    private String password;

    public String role;

    public UserDTO() {
    }
}
