package com.sicuga.sicugaserver.dto;

import com.sicuga.sicugaserver.model.QuizDtl;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizDTOResponse {

    private  String id;

    private String name;

    private String description;

    private List<QuizDtl> listParam;

    private String updatedAt;
}
