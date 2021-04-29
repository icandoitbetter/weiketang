package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class Teacher extends User {

    public Teacher(){
        this.setUserType("TEACHER");
    }
}
