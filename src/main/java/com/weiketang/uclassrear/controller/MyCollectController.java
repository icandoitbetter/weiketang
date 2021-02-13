package com.weiketang.uclassrear.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyCollectController {
    @GetMapping("/myCollect")
    public String myCollect(){
        return "myCollect/myCollect";
    }
}
