package com.sicuga.sicugaserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneralUserAuthDTO {

    private String id;

    @NotEmpty(message = "Name is required.")
    private String name;

    @Email(message = "Email is required.")
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;

    @NotNull(message = "online is required")
    private boolean online;
}
