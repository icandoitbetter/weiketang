package com.weiketang.uclassrear.controller;

import com.weiketang.uclassrear.entity.Student;
import com.weiketang.uclassrear.entity.User;
import com.weiketang.uclassrear.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserManageController {

    @Autowired
    UserService userService;

    @GetMapping (value = {"/", "/login"})
    public String login(){
        /*自动跳转到登陆页面；*/
        return "login";
    }

    @PostMapping("/login")
    public String homePageRedirect(User user, HttpSession session, Model model){
        if(user.getUserId().length() < 10){
            model.addAttribute("msg", "用户名不存在");
            return "login";
        }

        User loginUser = userService.loginCheck(user.getUserId(), user.getPassword());

        if(loginUser != null){
            //session保存当前用户浏览器与服务器的会话信息，类似于cookie？
            session.setAttribute("userType", loginUser.getUserType());
            session.setAttribute("userId", loginUser.getUserId());
        }
        else {
            model.addAttribute("msg", "用户名或密码错误");
            return "login";
        }

        /*redirect是重定向，防止表单重复提交；*/
        return "redirect:/home";
    }

    @GetMapping("home")
    public String goToHomePage(Model model){
        /*主页就是 homePage.html；*/

        return "homePage";
    }

    @GetMapping("/myLecture")
    public String myLecture(HttpSession session){
        if(session.getAttribute("userType").equals(new Student().getUserType())){
            return "redirect:/myLecture";
        }
        return "redirect:/myLecture";
    }

}
