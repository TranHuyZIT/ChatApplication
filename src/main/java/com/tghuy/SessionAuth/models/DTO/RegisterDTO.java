package com.tghuy.SessionAuth.models.DTO;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotBlank(message = "Username cannot be blank")
    String username;
    @NotBlank(message = "Password cannot be blank")
    String password;
    @AssertTrue(message = "Must be checked")
    Boolean checked = false;
    @NotBlank(message = "Fullname cannot be blank")
    String fullName;
    @Email(message = "Email is invalid")
    @NotBlank(message = "Email cannot be blank")
    String email;
}
