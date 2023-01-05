package com.sicuga.sicugaserver.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneralUserAuthDTO {

    private String id;

    private String name;

    private String email;

    private String password;

    private boolean enable;
}
