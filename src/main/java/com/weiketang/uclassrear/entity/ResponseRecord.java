package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "responseRecord")
public class ResponseRecord{
    @Field("_id")
    private String _id;

    private String respondentId = "null";
    private String examId = "null";
    private String questionId = "null";
    private String questionType = "null";
    private String questionRecordId = "null";

    private String response = "null";
    private int grade = 0;

    private String submitDate = "null";

}
