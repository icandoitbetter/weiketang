package com.weiketang.uclassrear.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LectureSearchController {

    @GetMapping("/lectureSearch")
    public String lectureSearch(){
        return "lectureSearch/lectureSearch";
    }
}
