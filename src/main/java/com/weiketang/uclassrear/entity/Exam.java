package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Exam {
    @Id
    private String examId;

    private String publisherId;
    private String courseId;
    private String examTitle;
    private String examDescription;
    private int fullScore;
    private String deadline;
    private String term;
    private String tips;

    public String toString(){
        return this.term + this.examTitle;
    }
}
