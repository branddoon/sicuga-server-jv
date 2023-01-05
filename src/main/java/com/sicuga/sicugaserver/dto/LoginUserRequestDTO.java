package com.sicuga.sicugaserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequestDTO {

    private String email;

    private String password;
}
