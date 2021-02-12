package com.weiketang.uclassrear.service;

import com.weiketang.uclassrear.dao.studentDao;
import com.weiketang.uclassrear.dao.studentDaoImp;
import com.weiketang.uclassrear.entity.Student;
import com.weiketang.uclassrear.entity.loginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImp implements StudentService{
    @Autowired
    private studentDao studentDao01;

    public boolean hasStudentLoginSuccess(loginUser user){
        Student student = studentDao01.findByStudentId(user);
        if((student != null) && (student.password.equals(user.password))){
            return true;
        }
        return false;
    }
}
