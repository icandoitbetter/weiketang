package com.weiketang.uclassrear.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class myLectureController {
    @GetMapping("/myLecture")
    public String myLecture(HttpSession session){
        if(session.getAttribute("userRole").equals("STUDENT")){
            return "myLecture/myLecture_student";
        }

        return "myLecture/myLecture_teacher";
    }
}
