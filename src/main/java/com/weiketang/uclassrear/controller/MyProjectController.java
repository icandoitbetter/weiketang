package com.weiketang.uclassrear.controller;

import com.weiketang.uclassrear.entity.*;
import com.weiketang.uclassrear.service.MyProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
public class MyProjectController {
    @Autowired
    MyProjectService myProjectService;

    @GetMapping("/createOneProject")
    public String createOneProject(HttpSession session){

        String publisherId = session.getAttribute("userId").toString();
        MyProject project = new MyProject();   /*请前端传入用户输入的项目信息；*/
        myProjectService.createOneProject(publisherId, project);

        return "redirect:/myLecture";
    }

    @GetMapping("/removeOneProjectById")
    public String removeOneProjectById(){

        String projectId = "null";   /*请前端传入项目的id*/
        myProjectService.removeOneProjectById(projectId);

        return "redirect:/myLecture";
    }

    @GetMapping("/getProjectsByPublisher")
    public String getProjectsByPublisher(HttpSession session, Model model){

        String publisherId = session.getAttribute("userId").toString();
        List<MyModel> projects = myProjectService.getProjectsByPublisher(publisherId);
        model.addAttribute("projects1", projects);

        return "redirect:/myLecture";
    }

    @GetMapping("/getAllProjects")
    public String getAllProjects(Model model){

        List<MyModel> projects = myProjectService.getAllProjects();
        model.addAttribute("projects2", projects);

        return "redirect:/myLecture";
    }

    @GetMapping("/searchProjects")
    public String searchProjects(Model model){

        String inputStr = "null";   /*请前端传入用户输入的搜索内容*/
        List<MyModel> projects = myProjectService.searchProjects(inputStr);
        model.addAttribute("projects3", projects);

        return "redirect:/myLecture";
    }

    @GetMapping("/collectOneProject")
    public String collectOneProject(HttpSession session){

        String projectId = "null";   /*请前端传入项目的id*/
        String userId = session.getAttribute("userId").toString();
        myProjectService.collectOneProject(projectId, userId);

        return "redirect:/myLecture";
    }

    @GetMapping("/getFavorProjectsByUserId")
    public String getFavorProjectsByUserId(HttpSession session, Model model){

        String userId = session.getAttribute("userId").toString();
        List<MyFavorModel> favorModels = myProjectService.getFavorProjectsByUserId(userId);
        model.addAttribute("favorModels", favorModels);

        return "redirect:/myLecture";
    }

    @GetMapping("/removeOneFavorProject")
    public String removeOneFavorProject(HttpSession session){

        String projectId = "null";   /*请前端传入项目的id*/
        String userId = session.getAttribute("userId").toString();
        myProjectService.removeOneFavorProject(projectId, userId);

        return "redirect:/myLecture";
    }


}
