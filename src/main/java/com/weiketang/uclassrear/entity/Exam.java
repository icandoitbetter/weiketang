package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "exam")
public class Exam extends MyActivity{
    private String term = "null";
    private int fullScore = 0;

    public Exam(){
        this.setActivityType("EXAM");
    }
}
