package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "questionRecord")
public class QuestionRecord {
    @Id
    private String _id;

    private String examId = "null";
    private String questionType = "null";
    private String questionId = "null";

}
