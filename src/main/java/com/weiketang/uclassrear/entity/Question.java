package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "question")
public class Question {
    @Id
    private String _id;

    private String questionId = "null";
    private String authorId = "null";
    private String questionType = "null";

    private String subject = "null";
    private String content = "null";
    private String answer = "null";
    private String analysis = "null";

    private String optA = "null";
    private String optB = "null";
    private String optC = "null";
    private String optD = "null";

    private int score = 0;

}
