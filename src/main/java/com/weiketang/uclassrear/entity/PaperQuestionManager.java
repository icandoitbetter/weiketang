package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class PaperQuestionManager {
    private String examId;
    private String questionType;
    private String questionId;
}
