package com.weiketang.uclassrear.service;

import com.weiketang.uclassrear.dao.UserDao;
import com.weiketang.uclassrear.entity.Student;
import com.weiketang.uclassrear.entity.Teacher;
import com.weiketang.uclassrear.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    UserDao userDao;
    @Override
    public User loginCheck(String userId, String password) {
        if(userId == null || password == null) return null;
        return userDao.loginCheck(userId, password);
    }

    @Override
    public User updateUserInfo(User user) {
        if(user == null) return null;
        return userDao.updateUserInfo(user);
    }

    @Override
    public User loginStudent(User user) {
        if(user == null) return null;
        user.setUserType(new Student().getUserType());
        return userDao.insertOneUser(user);
    }

    @Override
    public User loginTeacher(User user) {
        if(user == null) return null;
        user.setUserType(new Teacher().getUserType());
        return userDao.insertOneUser(user);
    }

    @Override
    public User getCurrentUserInfo(HttpSession session) {
        if(session == null) return null;
        return userDao.getOneUserById(session.getAttribute("userId").toString());
    }
}
