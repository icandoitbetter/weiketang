package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class Student extends User {

    public Student(){
        this.setUserType("STUDENT");
    }
}
