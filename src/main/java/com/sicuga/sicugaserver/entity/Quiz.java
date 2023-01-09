package com.sicuga.sicugaserver.entity;

import com.sicuga.sicugaserver.model.QuizDtl;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document("Quiz")
public class Quiz {

    @Id
    @MongoId(FieldType.STRING)
    private String id;

    @Field("user")
    private ObjectId generalUserAuth;

    private String name;

    private String description;

    private List<QuizDtl> listParam;

    private Date updatedAt;

    private Date createdAt;

}
