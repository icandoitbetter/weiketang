package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("file")
public class MyFile {
    @Id
    private String fileId;

    private String uploaderId;
    private String uploaderDate;
    private String fileTitle;
    private long size;
    private String fileName;
    private String storePath;
    private String fileDescription;
}
