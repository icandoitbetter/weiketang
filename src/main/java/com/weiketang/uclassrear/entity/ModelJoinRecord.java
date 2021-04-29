package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "modelJoinRecord")
public class ModelJoinRecord {
    private String participatorId = "null";
    private String modelId = "null";
    private String modelType = "null";
}
