package com.weiketang.uclassrear.service;

import com.weiketang.uclassrear.entity.User;

import javax.servlet.http.HttpSession;

public interface UserService {
    /*用户登录时，密码检查与身份认证*/
    User loginCheck(String userId, String password);
    /*更新用户信息（用户自己更新自己的信息）*/
    User updateUserInfo(User user);
    /*学生登录*/
    User loginStudent(User user);
    /*教师登录*/
    User loginTeacher(User user);
    /*获取当前登录用户的信息（用户查看自己的信息）*/
    User getCurrentUserInfo(HttpSession session);
}
