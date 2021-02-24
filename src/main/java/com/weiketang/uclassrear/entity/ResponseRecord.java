package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ResponseRecord {
    @Id
    private String responseRecordId;

    private String respondentId;
    private String examId;
    private String questionType;
    private String questionId;
    private String response;
    private int grade;
    private String submitDate;
}
