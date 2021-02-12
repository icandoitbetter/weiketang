package com.weiketang.uclassrear.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Exam {
    private String examId;
    private String paperId;
    private String courseId;
    private String examTitle;
    private String examDescription;
    private int fullScore;
    private Date deadline;
    private String term;
    private String tips;
}
