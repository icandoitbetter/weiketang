package com.weiketang.uclassrear.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("file")
public class CourseFile extends MyFile {
    public CourseFile(){
        this.setModelType("COURSE");
    }
}
