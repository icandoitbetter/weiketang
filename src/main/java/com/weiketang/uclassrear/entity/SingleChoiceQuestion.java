package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "question")
public class SingleChoiceQuestion extends Question{

    public SingleChoiceQuestion(){
        this.setQuestionType("SINGLECHOICE");
    }

}
