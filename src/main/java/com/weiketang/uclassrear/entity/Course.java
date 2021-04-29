package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "model")
public class Course extends MyModel {

    public Course(){
        this.setModelType("COURSE");
    }
}
