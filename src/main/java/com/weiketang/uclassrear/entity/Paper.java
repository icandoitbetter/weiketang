package com.weiketang.uclassrear.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Paper {
    private String paperId;
    private String studentId;
    private String[] MQuestionId;
    private String[] FQuestionId;
    private String[] JQuestionId;
    private Date submitDate;
    private int score;
}
