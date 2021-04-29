package com.weiketang.uclassrear.service;

import com.weiketang.uclassrear.entity.User;

import javax.servlet.http.HttpSession;

public interface UserService {
    User loginCheck(String userId, String password);
    User updateUserInfo(User user);
    User loginStudent(User user);
    User loginTeacher(User user);
    User getCurrentUserInfo(HttpSession session);
}
