package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class JudgeQuestion extends Question{

    public JudgeQuestion(){
        this.type = "JudgeQuestion";
    }

    public String toString(){
        return this.question + " " + this.answer;
    }
}
