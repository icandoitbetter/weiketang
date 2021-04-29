package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "question")
public class MultiChoiceQuestion extends Question{

    public MultiChoiceQuestion(){
        this.setQuestionType("MULTICHOICE");
    }

}
