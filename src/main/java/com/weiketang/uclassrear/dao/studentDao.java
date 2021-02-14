package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.Student;
import com.weiketang.uclassrear.entity.loginUser;

public interface StudentDao {
    Student findByStudentId(loginUser user);
}
