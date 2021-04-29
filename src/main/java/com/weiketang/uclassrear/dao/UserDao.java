package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.User;

public interface UserDao {
    User loginCheck(String userId, String password);
    User updateUserInfo(User user);
    User insertOneUser(User user);
    User getOneUserById(String userId);
}
