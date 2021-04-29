package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("file")
public class MyFile {
    @Id
    private String _id;

    private String uploaderId = "null";
    private String uploaderDate = "null";
    private String fileTitle = "null";
    private long size = 0;
    private String fileName = "null";
    private String description = "null";

    private String modelType = "null";
    private String modelId = "null";

    /*存储路径：warehousePath + "\\" + modelType + "\\" + modelId + "\\" + fileName*/
}
