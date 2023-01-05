package com.sicuga.sicugaserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JWTAuthResponseDTO {

    private String id;
    private String name;
    private String email;
    private String token;
    public JWTAuthResponseDTO() {

    }
}
