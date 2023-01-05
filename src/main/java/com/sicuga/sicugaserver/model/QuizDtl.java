package com.sicuga.sicugaserver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizDtl{

    private String id;

    private String inputTask;

    private String filePhoto;

    private long barPoints;

    private long barTime;

    private String inAnswerOne;

    private String inAnswerTwo;

    private String inAnswerThree;

    private String inAnswerFour;

    private byte valAnswerOne;

    private byte valAnswerTwo;

    private byte valAnswerThree;

    private byte valAnswerFour;

}
