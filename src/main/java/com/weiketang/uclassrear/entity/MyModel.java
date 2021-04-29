package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "model")
public class MyModel {
    @Id
    private String _id;

    private String modelId = "null";
    private String publisherId = "null";
    private String modelType = "null";

    private String title = "null";
    private String subject = "null";
    private String description = "null";
    private String term = "null";

}
