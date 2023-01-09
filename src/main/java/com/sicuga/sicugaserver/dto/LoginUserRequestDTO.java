package com.sicuga.sicugaserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequestDTO {

    @Email(message = "Email is required.")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;
}
