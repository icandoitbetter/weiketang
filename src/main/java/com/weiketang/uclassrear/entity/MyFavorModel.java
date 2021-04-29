package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "myFavorModel")
public class MyFavorModel {
    @Id
    private String _id;
    private String userId;
    private String modelId;
    private String modelType;
    private String subject;
}
