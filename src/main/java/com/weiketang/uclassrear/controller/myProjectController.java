package com.weiketang.uclassrear.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class myProjectController {

    @GetMapping("/myProject")
    public String myProject(){
        return "myProject/myProject";
    }
}
