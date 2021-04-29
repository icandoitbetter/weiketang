package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "model")
public class MyProject extends MyModel{
    public MyProject(){
        this.setModelType("PROJECT");
    }
}
