package com.weiketang.uclassrear.service;

import com.weiketang.uclassrear.dao.TeacherDao;
import com.weiketang.uclassrear.entity.Teacher;
import com.weiketang.uclassrear.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImp implements TeacherService{
    @Autowired
    private TeacherDao teacherDao01;

    public boolean hasTeacherLoginSuccess(LoginUser user){
        Teacher teacher = teacherDao01.findByTeacherId(user);
        if((teacher != null) && (teacher.password.equals(user.password))){
            return true;
        }
        return false;
    }
}
