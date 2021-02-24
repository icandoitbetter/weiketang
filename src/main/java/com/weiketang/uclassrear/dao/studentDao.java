package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.Student;
import com.weiketang.uclassrear.entity.LoginUser;

public interface StudentDao {
    Student findByStudentId(LoginUser user);
}
