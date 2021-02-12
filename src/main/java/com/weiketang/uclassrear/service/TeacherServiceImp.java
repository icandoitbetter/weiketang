package com.weiketang.uclassrear.service;

import com.weiketang.uclassrear.dao.studentDaoImp;
import com.weiketang.uclassrear.dao.teacherDao;
import com.weiketang.uclassrear.dao.teacherDaoImp;
import com.weiketang.uclassrear.entity.Teacher;
import com.weiketang.uclassrear.entity.loginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImp implements TeacherService{
    @Autowired
    private teacherDao teacherDao01;

    public boolean hasTeacherLoginSuccess(loginUser user){
        Teacher teacher = teacherDao01.findByTeacherId(user);
        if((teacher != null) && (teacher.password.equals(user.password))){
            return true;
        }
        return false;
    }
}
