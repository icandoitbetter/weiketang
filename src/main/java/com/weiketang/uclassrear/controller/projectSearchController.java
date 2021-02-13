package com.weiketang.uclassrear.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class projectSearchController {

    @GetMapping("/projectSearch")
    public String projectSearch(){
        return "projectSearch/projectSearch";
    }
}
