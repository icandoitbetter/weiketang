package com.weiketang.uclassrear.entity;

import lombok.Data;

@Data
public class JudgeQuestion {
    private String JQuestionId;
    private String subject;
    private String content;
    private String analysis;
    private String[] answer;
    private int fullScore;
    private int score;
}
