package com.weiketang.uclassrear.entity;

import lombok.Data;

import java.util.Date;

@Data
public class File {
    private String fileId;
    private String uploaderId;
    private Date uploaderDate;
    private String fileTitle;
    private String type;
    private int size;
    private String storePath;
    private String fileDescription;
}
