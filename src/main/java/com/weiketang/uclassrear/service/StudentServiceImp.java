package com.weiketang.uclassrear.service;

import com.weiketang.uclassrear.dao.StudentDao;
import com.weiketang.uclassrear.entity.Student;
import com.weiketang.uclassrear.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImp implements StudentService{
    @Autowired
    private StudentDao studentDao01;

    public boolean hasStudentLoginSuccess(LoginUser user){
        Student student = studentDao01.findByStudentId(user);
        if((student != null) && (student.password.equals(user.password))){
            return true;
        }
        return false;
    }
}
