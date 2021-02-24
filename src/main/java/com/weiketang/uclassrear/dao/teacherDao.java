package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.Teacher;
import com.weiketang.uclassrear.entity.LoginUser;

public interface TeacherDao {
    Teacher findByTeacherId(LoginUser user);
}
