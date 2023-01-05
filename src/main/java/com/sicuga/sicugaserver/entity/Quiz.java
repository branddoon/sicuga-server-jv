package com.sicuga.sicugaserver.entity;

import com.sicuga.sicugaserver.model.QuizDtl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Document("Quiz")
public class Quiz {

    @Id
    @MongoId(FieldType.STRING)
    private String id;

    private GeneralUserAuth generalUserAuth;

    private String name;

    private String description;

    private List<QuizDtl> listParam;

    private ZonedDateTime zonedDateTime;
}
