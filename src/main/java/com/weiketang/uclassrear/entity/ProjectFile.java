package com.weiketang.uclassrear.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("file")
public class ProjectFile extends MyFile {
    public ProjectFile(){
        this.setModelType("PROJECT");
    }
}
