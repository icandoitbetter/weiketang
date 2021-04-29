package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class MyActivity {
    @Id
    private String _id;
    private String activityId = "null";

    private String publisherId = "null";
    private String modelId = "null";
    private String activityType = "null";

    private String title = "null";
    private String description = "null";
    private String deadline = "null";
    private String tips = "null";

}
