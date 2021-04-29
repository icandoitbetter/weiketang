package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImp implements UserDao{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public User loginCheck(String userId, String password) {
        return mongoTemplate.findOne(
                new Query(Criteria.where("userId").is(userId)
                        .and("password").is(password)),
                User.class
        );
    }

    @Override
    public User updateUserInfo(User user) {
        if(user.getUserId() == null) return null;
        return mongoTemplate.findAndModify(
                new Query(Criteria.where("userId").is(user.getUserId())),
                new Update().set("password", user.getPassword())
                            .set("nickName", user.getNickName())
                            .set("phone", user.getPhone())
                            .set("email", user.getEmail()),
                User.class
        );
    }

    @Override
    public User insertOneUser(User user) {
        if(user.getUserId() == null || user.getPassword() == null){
            return null;
        }

        User thisUser = mongoTemplate.findOne(
                new Query(Criteria.where("userId").is(user.getUserId())),
                User.class
        );
        if(thisUser != null) return null;

        return mongoTemplate.insert(user);
    }

    @Override
    public User getOneUserById(String userId) {
        return mongoTemplate.findOne(
                new Query(Criteria.where("userId").is(userId)),
                User.class
        );
    }
}
