package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SingleChoiceQuestion extends Question{

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public SingleChoiceQuestion(){
        this.type = "SingleChoiceQuestion";
    }

    public String toString() {
        return this.question +
                " A. " + this.optionA +
                " B. " + this.optionB +
                " C. " + this.optionC +
                " D. " + this.optionD +
                " answer: " + this.answer;
    }
}
