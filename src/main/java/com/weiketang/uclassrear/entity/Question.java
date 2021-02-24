package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Question {
    @Id
    public String questionId;

    public String paperId;
    public String subject;
    public String authorId;
    public String type;

    public String question;
    public String answer;
    public String analysis;

    public int score;

}
