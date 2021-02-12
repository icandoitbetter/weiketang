package com.weiketang.uclassrear.controller;

import com.weiketang.uclassrear.entity.loginUser;
import com.weiketang.uclassrear.service.StudentServiceImp;
import com.weiketang.uclassrear.service.TeacherServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class pageRouteController {
    @Autowired
    StudentServiceImp studentServiceImp01;
    @Autowired
    TeacherServiceImp teacherServiceImp01;

    @GetMapping (value = {"/", "/login"})
    public String login(){
        return "login";
    }

    // 重定向，防止表单重复提交；
    @PostMapping("/login")
    public String homePageRedirect(loginUser user, HttpSession session, Model model){
        if(user.userId.length() < 10){
            model.addAttribute("msg", "用户名不存在");
            return "login";
        }
        boolean isStudentUser = studentServiceImp01.hasStudentLoginSuccess(user);
        boolean isTeacherUser = teacherServiceImp01.hasTeacherLoginSuccess(user);
        if((isStudentUser != true) && (isTeacherUser != true)){
            model.addAttribute("msg", "用户名或密码错误");
            return "login";
        }

        //session保存当前用户浏览器与服务器的会话信息，类似于cookie？
        session.setAttribute("loginUser", user);
        if(isStudentUser == true){
            session.setAttribute("userRole", "STUDENT");
        }
        else{
            session.setAttribute("userRole", "TEACHER");
        }

        return "redirect:/main.html";
    }

    @GetMapping("main.html")
    public String goToHomePage(){
        return "homePage";
    }
}
