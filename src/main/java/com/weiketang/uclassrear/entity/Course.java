package com.weiketang.uclassrear.entity;

import lombok.Data;

@Data
public class Course {
    private String courseId;
    private String teacherId;
    private String courseTitle;
    private String subject;
    private String courseDescription;
    private String term;
}
