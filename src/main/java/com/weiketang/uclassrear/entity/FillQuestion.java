package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class FillQuestion extends Question{

    public FillQuestion(){
        this.type = "FillQuestion";
    }

    public String toString(){
        return this.question + " " + this.answer;
    }
}
