package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.Teacher;
import com.weiketang.uclassrear.entity.loginUser;

public interface TeacherDao {
    Teacher findByTeacherId(loginUser user);
}
