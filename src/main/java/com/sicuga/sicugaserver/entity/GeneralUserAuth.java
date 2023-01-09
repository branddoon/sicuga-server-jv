package com.sicuga.sicugaserver.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document("User")
public class GeneralUserAuth {

    @Id
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String name;

    private String email;

    private String password;

    private boolean online;

}
