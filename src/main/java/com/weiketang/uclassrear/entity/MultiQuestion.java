package com.weiketang.uclassrear.entity;

import lombok.Data;

@Data
public class MultiQuestion {
    private String MQuestionId;
    private String subject;
    private String content;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String analysis;
    private String[] answer;
    private int fullScore;
}
