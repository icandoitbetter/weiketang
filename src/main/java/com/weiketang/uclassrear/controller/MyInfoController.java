package com.weiketang.uclassrear.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MyInfoController {
    @GetMapping("/myInfo")
    public String myInfo(HttpSession session){
        if(session.getAttribute("userRole").equals("STUDENT")){
            return "myInfo/myInfo_student";
        }
        return "myInfo/myInfo_teacher";
    }
}
